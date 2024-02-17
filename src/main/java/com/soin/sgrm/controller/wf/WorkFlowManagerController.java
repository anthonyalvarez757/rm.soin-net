package com.soin.sgrm.controller.wf;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.soin.sgrm.model.EmailTemplate;
import com.soin.sgrm.model.Errors_RFC;
import com.soin.sgrm.model.Errors_Release;
import com.soin.sgrm.model.Project;
import com.soin.sgrm.model.RFCError;
import com.soin.sgrm.model.RFC_WithoutRelease;
import com.soin.sgrm.model.Release;
import com.soin.sgrm.model.ReleaseError;
import com.soin.sgrm.model.ReleaseTracking;
import com.soin.sgrm.model.ReleaseTrackingShow;
import com.soin.sgrm.model.Release_RFC;
import com.soin.sgrm.model.Release_RFCFast;
import com.soin.sgrm.model.Releases_WithoutObj;
import com.soin.sgrm.model.Request;
import com.soin.sgrm.model.Status;
import com.soin.sgrm.model.StatusRFC;
import com.soin.sgrm.model.System;
import com.soin.sgrm.model.SystemUser;
import com.soin.sgrm.model.TypePetition;
import com.soin.sgrm.model.TypeRequest;
import com.soin.sgrm.model.wf.Node;
import com.soin.sgrm.model.wf.NodeName;
import com.soin.sgrm.model.wf.NodeRFC;
import com.soin.sgrm.model.wf.WFRFC;
import com.soin.sgrm.model.wf.WFRelease;
import com.soin.sgrm.model.wf.WorkFlow;
import com.soin.sgrm.service.EmailTemplateService;
import com.soin.sgrm.service.ErrorRFCService;
import com.soin.sgrm.service.ErrorReleaseService;
import com.soin.sgrm.service.ParameterService;
import com.soin.sgrm.service.ProjectService;
import com.soin.sgrm.service.RFCErrorService;
import com.soin.sgrm.service.RFCService;
import com.soin.sgrm.service.ReleaseErrorService;
import com.soin.sgrm.service.ReleaseService;
import com.soin.sgrm.service.RequestNewService;
import com.soin.sgrm.service.RequestService;
import com.soin.sgrm.service.SigesService;
import com.soin.sgrm.service.StatusRFCService;
import com.soin.sgrm.service.StatusService;
import com.soin.sgrm.service.SystemService;
import com.soin.sgrm.service.TypeRequestService;
import com.soin.sgrm.service.wf.NodeService;
import com.soin.sgrm.service.wf.WFReleaseService;
import com.soin.sgrm.service.wf.WFRFCService;
import com.soin.sgrm.utils.CommonUtils;
import com.soin.sgrm.utils.JsonResponse;
import com.soin.sgrm.utils.JsonSheet;
import com.soin.sgrm.utils.MyLevel;
import com.soin.sgrm.controller.BaseController;
import com.soin.sgrm.exception.Sentry;

@Controller
@RequestMapping("/manager/wf")
public class WorkFlowManagerController extends BaseController {

	public static final Logger logger = Logger.getLogger(WorkFlowManagerController.class);

	@Autowired
	private StatusService statusService;
	@Autowired
	private StatusRFCService statusRFCService;
	@Autowired
	private WFReleaseService wfReleaseService;
	@Autowired
	private WFRFCService wfrfcService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private NodeService nodeService;
	@Autowired
	private ParameterService paramService;
	@Autowired
	private EmailTemplateService emailService;
	@Autowired
	private ErrorReleaseService errorReleaseService;
	@Autowired
	private ErrorRFCService errorRFCService;
	@Autowired
	private ReleaseService releaseService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ReleaseErrorService releaseErrorService;
	@Autowired
	private RFCErrorService rfcErrorService;
	@Autowired
	private SigesService sigesService;
	@Autowired
	private RFCService rfcService;
	@Autowired
	private RequestService requestService;

	@Autowired
	private RequestNewService requestNewService;
	@Autowired
	private TypeRequestService typeRequestService;

	@RequestMapping(value = "/release/", method = RequestMethod.GET)
	public String indexRelease(HttpServletRequest request, Locale locale, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		try {
			String name = getUserLogin().getUsername();
			Object[] systemIds = systemService.myTeams(name);
			model.addAttribute("system", new SystemUser());
			model.addAttribute("systems", systemService.listSystemUserByIds(systemIds));
			model.addAttribute("status", new Status());
			model.addAttribute("statuses", statusService.list());
			model.addAttribute("errors", errorReleaseService.findAll());
			loadCountsRelease(request, systemIds, getUserLogin().getId());
		} catch (Exception e) {
			Sentry.capture(e, "wfReleaseManager");
			redirectAttributes.addFlashAttribute("data",
					"Error en la carga de la pagina inicial/systemas." + " ERROR: " + e.getMessage());
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
		}
		return "/wf/workFlow/workFlowManagerRelease";

	}

	@RequestMapping(value = "/tpo/", method = RequestMethod.GET)
	public String indexTPOs(HttpServletRequest request, Locale locale, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		try {
			Integer userLogin = getUserLogin().getId();
			List<System> systems = systemService.findByUserIncidence(userLogin);
			List<Project> projects = new ArrayList<>();

			for (System system : systems) {
				system.getProyect();
				boolean veri = true;
				for (Project project : projects) {
					if (project.getId() == system.getProyect().getId()) {
						veri = false;
					}
				}
				if (veri) {
					projects.add(system.getProyect());
				}
			}
			model.addAttribute("projects", projects);
			model.addAttribute("project", new Project());
			model.addAttribute("typeRequests", typeRequestService.list());
			model.addAttribute("typeRequest", new TypeRequest());
		} catch (Exception e) {
			Sentry.capture(e, "wfTPOManagement");
			redirectAttributes.addFlashAttribute("data",
					"Error en la carga de la pagina inicial/systemas." + " ERROR: " + e.getMessage());
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
		}
		return "/wf/request/request";

	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = { "/listTpos" }, method = RequestMethod.GET)
	public @ResponseBody JsonSheet listTPOs(HttpServletRequest request, Locale locale, Model model) {
		JsonSheet<?> requests = new JsonSheet<>();
		try {

			Integer sEcho = Integer.parseInt(request.getParameter("sEcho"));
			Integer iDisplayStart = Integer.parseInt(request.getParameter("iDisplayStart"));
			Integer iDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
			String sSearch = request.getParameter("sSearch");
			int proyectId;
			int typeId;
			Integer userLogin = getUserLogin().getId();
			if (request.getParameter("proyectFilter").equals("")) {
				proyectId = 0;
			} else {
				proyectId = Integer.parseInt(request.getParameter("proyectFilter"));
			}

			if (request.getParameter("typeRequestFilter").equals("")) {
				typeId = 0;
			} else {
				typeId = Integer.parseInt(request.getParameter("typeRequestFilter"));
			}

			requests = requestNewService.findAll(sEcho, iDisplayStart, iDisplayLength, sSearch, proyectId, typeId,
					userLogin);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return requests;

	}

	@RequestMapping(value = "/updateRequest/{id}", method = RequestMethod.PUT)
	public @ResponseBody JsonResponse updateRequest(@PathVariable Integer id, Model model) {
		JsonResponse res = new JsonResponse();
		try {
			Request requestUpt = requestNewService.findById(id);
			if (requestUpt.getAuto() == 1) {
				requestUpt.setAuto(0);
				requestUpt.setNodeName("");
				requestUpt.setMotive("");
				res.setMessage("Aprobacion automatica desactivada!");
			} else {
				requestUpt.setAuto(1);
				res.setMessage("Aprobacion automatica activada!");
			}
			res.setStatus("success");
			requestNewService.update(requestUpt);

		} catch (Exception e) {
			Sentry.capture(e, "typePetition");
			res.setStatus("exception");
			res.setMessage("Error al modificaar el Tipo solicitud!");
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
		}
		return res;
	}

	@RequestMapping(value = "/updateRequestStatus", method = RequestMethod.POST)
	public @ResponseBody JsonResponse draftRelease(HttpServletRequest request, Model model,
			@RequestParam(value = "idRequest", required = true) Integer idRequest,
			@RequestParam(value = "nodeName", required = true) String nodeName,
			@RequestParam(value = "motive", required = false) String motive) {
		JsonResponse res = new JsonResponse();
		try {

			Request requestOrigin = requestNewService.findById(idRequest);
			requestOrigin.setNodeName(nodeName);
			requestOrigin.setMotive(motive);
			requestOrigin.setAuto(1);
			requestNewService.update(requestOrigin);
			res.setObj(requestOrigin);
			res.setMessage("Aprobacion automatica activada!");
			res.setStatus("success");

		} catch (Exception e) {
			Sentry.capture(e, "request");
			res.setStatus("error");
			res.setException("Error al modificar requerimiento: " + e.toString());
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
		}
		return res;
	}

	@RequestMapping(value = "/listNodeName/{id}", method = RequestMethod.GET)
	public @ResponseBody List<NodeName> listNodeName(@PathVariable Integer id, Model model) {

		try {
			Integer userLogin = getUserLogin().getId();
			List<NodeName> listNode = nodeService.listNodeNames(id, userLogin);

			return listNode;
		} catch (Exception e) {

			logger.log(MyLevel.RELEASE_ERROR, e.toString());
		}
		return null;
	}

	@RequestMapping(value = "/rfc/", method = RequestMethod.GET)
	public String indexRFC(HttpServletRequest request, Locale locale, Model model, HttpSession session,
			RedirectAttributes redirectAttributes) {
		try {
			String name = getUserLogin().getUsername();
			Object[] systemIds = systemService.myTeams(name);
			model.addAttribute("system", new SystemUser());
			model.addAttribute("systems", systemService.listSystemUserByIds(systemIds));
			model.addAttribute("status", new Status());
			model.addAttribute("statuses", statusRFCService.findAll());
			model.addAttribute("errors", errorReleaseService.findAll());
			loadCountsRFC(request, systemIds);
		} catch (Exception e) {
			Sentry.capture(e, "wfReleaseManager");
			redirectAttributes.addFlashAttribute("data",
					"Error en la carga de la pagina inicial/systemas." + " ERROR: " + e.getMessage());
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
		}
		return "/wf/workFlow/workFlowManagerRFC";

	}

	@RequestMapping(path = "/workFlowRelease", method = RequestMethod.GET)
	public @ResponseBody JsonSheet<?> getSystemRelease(HttpServletRequest request, Locale locale, Model model,
			HttpSession session) {
		try {
			String range = request.getParameter("dateRange");
			String[] dateRange = (range != null) ? range.split("-") : null;
			Integer systemId = Integer.parseInt(request.getParameter("systemId"));
			Integer statusId = Integer.parseInt(request.getParameter("statusId"));

			String name = getUserLogin().getUsername(), sSearch = request.getParameter("sSearch");
			Integer idUser = getUserLogin().getId();
			int sEcho = Integer.parseInt(request.getParameter("sEcho")),
					iDisplayStart = Integer.parseInt(request.getParameter("iDisplayStart")),
					iDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
			return wfReleaseService.listWorkFlowManager(name, sEcho, iDisplayStart, iDisplayLength, sSearch, null,
					dateRange, systemId, statusId, systemService.myTeams(name), idUser);
		} catch (Exception e) {
			Sentry.capture(e, "wfReleaseManager");
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
			return null;
		}
	}

	@RequestMapping(path = "/workFlowRFC", method = RequestMethod.GET)
	public @ResponseBody JsonSheet<?> getSystemRFC(HttpServletRequest request, Locale locale, Model model,
			HttpSession session) {
		try {
			String range = request.getParameter("dateRange");
			String[] dateRange = (range != null) ? range.split("-") : null;
			Integer systemId = Integer.parseInt(request.getParameter("systemId"));
			Long statusId = (long) Integer.parseInt(request.getParameter("statusId"));

			String name = getUserLogin().getUsername(), sSearch = request.getParameter("sSearch");
			int sEcho = Integer.parseInt(request.getParameter("sEcho")),
					iDisplayStart = Integer.parseInt(request.getParameter("iDisplayStart")),
					iDisplayLength = Integer.parseInt(request.getParameter("iDisplayLength"));
			return wfrfcService.listWorkFlowManager(name, sEcho, iDisplayStart, iDisplayLength, sSearch, null,
					dateRange, systemId, statusId, systemService.myTeams(name));
		} catch (Exception e) {
			Sentry.capture(e, "wfRFCManager");
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
			return null;
		}
	}

	@RequestMapping(value = "/wfStatus", method = RequestMethod.POST)
	public @ResponseBody JsonResponse draftRelease(HttpServletRequest request, Model model,
			@RequestParam(value = "idRelease", required = true) Integer idRelease,
			@RequestParam(value = "idNode", required = true) Integer idNode,
			@RequestParam(value = "motive", required = true) String motive,
			@RequestParam(value = "idError", required = false) Long idError) {
		JsonResponse res = new JsonResponse();
		try {
			WFRelease release = wfReleaseService.findWFReleaseById(idRelease);
			Node node = nodeService.findById(idNode);
			Request requestVer = null;

			String newMotive = motive;
			node.getStatus().setMotive(newMotive);
			release.setNode(node);
			release.setStatus(node.getStatus());
			release.setOperator(getUserLogin().getFullName());

			if (node.getStatus().getName().equals("Error")) {
				Errors_Release error = errorReleaseService.findById(idError);
				ReleaseError releaseError = new ReleaseError();
				releaseError.setSystem(release.getSystem());
				releaseError.setProject(projectService.findById(release.getSystem().getProyectId()));
				releaseError.setError(error);
				Releases_WithoutObj releaseWithObj = releaseService.findReleaseWithouObj(release.getId());
				releaseError.setRelease(releaseWithObj);
				releaseError.setObservations(newMotive);
				releaseError.setErrorDate(CommonUtils.getSystemTimestamp());
				releaseErrorService.save(releaseError);
				wfReleaseService.wfStatusReleaseWithOutMin(release);
				Status statusChange = statusService.findByName("Borrador");
				release.setStatus(statusChange);

				if (statusChange != null && statusChange.getName().equals("Borrador")) {
					if (release.getStatus().getId() != node.getStatus().getId())
						release.setRetries(release.getRetries() + 1);
				}

				newMotive = "Paso a borrador por " + error.getName();
				node.getStatus().setMotive(newMotive);
				release.setNode(node);
				wfReleaseService.wfStatusRelease(release);

				// Si esta marcado para enviar correo
				if (node.getSendEmail()) {
					Integer idTemplate = Integer.parseInt(paramService.findByCode(21).getParamValue());
					EmailTemplate email = emailService.findById(idTemplate);
					WFRelease releaseEmail = release;
					Thread newThread = new Thread(() -> {
						try {
							emailService.sendMail(releaseEmail, email, motive);
						} catch (Exception e) {
							Sentry.capture(e, "release");
						}
					});

					newThread.start();
				}
				// Si esta marcado para enviar correo
				if (node.getUsers().size() > 0) {
					Integer idTemplate2 = Integer.parseInt(paramService.findByCode(23).getParamValue());

					EmailTemplate emailNotify = emailService.findById(idTemplate2);

					String user = getUserLogin().getFullName();
					Thread newThread = new Thread(() -> {
						try {
							emailService.sendMailNotify(release, emailNotify, user);
						} catch (Exception e) {
							Sentry.capture(e, "release");
						}

					});
					newThread.start();
				}

				// si tiene un nodo y ademas tiene actor se notifica por correo
				if (node != null && node.getActors().size() > 0) {
					Integer idTemplate3 = Integer.parseInt(paramService.findByCode(22).getParamValue());
					EmailTemplate emailActor = emailService.findById(idTemplate3);
					WFRelease releaseEmail = release;
					Thread newThread = new Thread(() -> {
						try {
							emailService.sendMailActor(releaseEmail, emailActor);
						} catch (Exception e) {
							Sentry.capture(e, "release");
						}

					});
					newThread.start();
				}
			} else {

				node.getStatus().setMotive(newMotive);
				release.setNode(node);
				release.setStatus(node.getStatus());
				release.setOperator(getUserLogin().getFullName());
				// Si esta marcado para enviar correo
				if (node.getSendEmail()) {
					Integer idTemplate = Integer.parseInt(paramService.findByCode(21).getParamValue());
					EmailTemplate email = emailService.findById(idTemplate);
					WFRelease releaseEmail = release;
					Thread newThread = new Thread(() -> {
						try {
							emailService.sendMail(releaseEmail, email, motive);
						} catch (Exception e) {
							Sentry.capture(e, "release");
						}
					});

					newThread.start();
				}
				int nodeId1 = node.getId();
				boolean firstTime = true;
				node = checkNode(node, release, requestVer, firstTime, newMotive);
				int nodeId2 = node.getId();
				release.setNode(node);
				release.setStatus(node.getStatus());

				// Si esta marcado para enviar correo
				if (node.getUsers().size() > 0) {
					Integer idTemplate2 = Integer.parseInt(paramService.findByCode(23).getParamValue());

					EmailTemplate emailNotify = emailService.findById(idTemplate2);

					String user = getUserLogin().getFullName();
					Thread newThread = new Thread(() -> {
						try {
							emailService.sendMailNotify(release, emailNotify, user);
						} catch (Exception e) {
							Sentry.capture(e, "release");
						}

					});
					newThread.start();
				}

				// si tiene un nodo y ademas tiene actor se notifica por correo
				if (node != null && node.getActors().size() > 0) {
					Integer idTemplate3 = Integer.parseInt(paramService.findByCode(22).getParamValue());
					EmailTemplate emailActor = emailService.findById(idTemplate3);
					WFRelease releaseEmail = release;
					Thread newThread = new Thread(() -> {
						try {
							emailService.sendMailActor(releaseEmail, emailActor);
						} catch (Exception e) {
							Sentry.capture(e, "release");
						}

					});
					newThread.start();
				}
				if (nodeId1 != nodeId2) {
					wfReleaseService.wfStatusRelease(release);
				} else {

				}

			}

			res.setStatus("success");
		} catch (Exception e) {
			Sentry.capture(e, "wfReleaseManagement");
			res.setStatus("exception");
			res.setException("Error al cambiar estado del release: " + e.getMessage());
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
		}
		return res;
	}

	private Node checkNode(Node node, WFRelease release, Request requestVer, boolean firstTime, String newMotive)
			throws SQLException {
		String motive = "";
		if (node != null) {
			if (requestVer != null) {
				if (!firstTime) {
					release.setStatus(node.getStatus());
					release.setMotive(requestVer.getMotive());
					release.setOperator("Automatico");
				}

				wfReleaseService.wfStatusRelease(release);
				firstTime = false;
				updateRelease(node, release, requestVer, motive);
				release.setNodeFinish(node);
				node = nodeService.findById(release.getNodeFinish().getSkipByRequestId());
				if (node == null) {
					node = nodeService.findById(release.getNodeFinish().getSkipReapproveId());
				}
				if (node == null) {
					node = nodeService.findById(release.getNodeFinish().getSkipByRequestId());
				}
				if (node == null) {
					node = nodeService.findById(release.getNodeFinish().getSkipId());
				}
				if (node == null) {
					node = release.getNodeFinish();
				}
				requestVer = null;
				return checkNode(node, release, requestVer, firstTime, motive);
			} else if (node.getSkipByRequest()) {

				if (!firstTime) {
					release.setStatus(node.getStatus());
					release.setOperator("Automatico");
					motive = node.getMotiveSkipR();
				} else {
					motive = newMotive;
				}
				updateRelease(node, release, requestVer, motive);
				firstTime = false;
				node = nodeService.findById(node.getSkipByRequestId());
				if (node == null) {
					node = nodeService.findById(release.getNodeFinish().getSkipReapproveId());
				}
				if (node == null) {
					node = nodeService.findById(release.getNodeFinish().getSkipByRequestId());
				}
				if (node == null) {
					node = nodeService.findById(release.getNodeFinish().getSkipId());
				}
				requestVer = null;
				return checkNode(node, release, requestVer, firstTime, motive);
			} else if (node.getSkipReapprove()) {
				if (!firstTime) {
					release.setStatus(node.getStatus());
					release.setOperator("Automatico");
					motive = node.getMotiveSkipRA();
				} else {
					motive = newMotive;
				}
				ReleaseTrackingShow tracking = releaseService.findReleaseTracking(release.getId());
				Set<ReleaseTracking> trackingList = tracking.getTracking();
				boolean verification = false;
				for (ReleaseTracking track : trackingList) {
					if (track.getStatus().equals("En Aprobacion")) {
						verification = true;// se verifica si ya paso por aprobacion si no se hace lo normal
					}
				}
				if (verification) {
					updateRelease(node, release, requestVer, motive);
					firstTime = false;
					node = nodeService.findById(node.getSkipReapproveId());

					if (node == null) {
						node = nodeService.findById(release.getNodeFinish().getSkipReapproveId());
					}
					if (node == null) {
						node = nodeService.findById(release.getNodeFinish().getSkipByRequestId());
					}
					if (node == null) {
						node = nodeService.findById(release.getNodeFinish().getSkipId());
					}
					requestVer = null;
					return checkNode(node, release, requestVer, firstTime, motive);
				} else {
					node = nodeService.findById(node.getSkipReapproveId());

					if (node == null) {
						node = nodeService.findById(release.getNodeFinish().getSkipReapproveId());
					}
					if (node == null) {
						node = nodeService.findById(release.getNodeFinish().getSkipByRequestId());
					}
					if (node == null) {
						node = nodeService.findById(release.getNodeFinish().getSkipId());
					}
					return node;
				}

			} else if (node.getSkipNode()) {
				if (!firstTime) {
					release.setStatus(node.getStatus());
					release.setOperator("Automatico");
					motive = node.getMotiveSkip();
				} else {
					motive = newMotive;
				}
				updateRelease(node, release, requestVer, motive);
				node = nodeService.findById(node.getSkipId());
				if (node == null) {
					node = nodeService.findById(release.getNodeFinish().getSkipReapproveId());
				}
				if (node == null) {
					node = nodeService.findById(release.getNodeFinish().getSkipByRequestId());
				}
				if (node == null) {
					node = nodeService.findById(release.getNodeFinish().getSkipId());
				}
				requestVer = null;
				return checkNode(node, release, requestVer, firstTime, motive);
			} else {
				return node;
			}
		}
		return null;

	}

	public void updateRelease(Node node, WFRelease release, Request requestVer, String motive) {

		release.setStatus(node.getStatus());
		if (requestVer == null) {
			release.setMotive(motive);
			Status statusNew = new Status();
			statusNew = node.getStatus();
			statusNew.setMotive(motive);
			node.setStatus(statusNew);
			release.setNode(node);
		} else {
			release.setMotive(node.getMotiveSkip());
			release.getNode().getStatus().setMotive(node.getMotiveSkip());

		}
		wfReleaseService.wfStatusRelease(release);
	}

	@RequestMapping(value = "/wfStatusRFC", method = RequestMethod.POST)
	public @ResponseBody JsonResponse draftRFC(HttpServletRequest request, Model model,
			@RequestParam(value = "idRFC", required = true) Long idRFC,
			@RequestParam(value = "idNode", required = true) Integer idNode,
			@RequestParam(value = "motive", required = true) String motive,
			@RequestParam(value = "idError", required = false) Long idError) {
		JsonResponse res = new JsonResponse();
		String newMotive = motive;
		try {
			WFRFC rfc = wfrfcService.findWFRFCById(idRFC);
			NodeRFC node = nodeService.findByIdNoRFC(idNode);

			node.getStatus().setReason(newMotive);

			rfc.setNode(node);
			rfc.setStatus(node.getStatus());

			String user = getUserLogin().getFullName();
			rfc.setOperator(user);
			if (node.getStatus() != null && node.getStatus().getName().equals("Borrador")) {
				Set<Release_RFCFast> releases = rfc.getReleases();
				for (Release_RFCFast release : releases) {
					release.setStatus(release.getStatusBefore());
					release.setMotive("Devuelto al estado " + release.getStatus().getName());
					releaseService.updateStatusReleaseRFC(release, user);
				}

				/*
				 * if (release.getStatus().getId() != status.getId())
				 * release.setRetries(release.getRetries() + 1);
				 */
			} else if (node.getStatus() != null && node.getStatus().getName().equals("Error")) {
				Errors_RFC error = errorRFCService.findById(idError);
				RFCError rfcError = new RFCError();
				rfcError.setSystem(rfc.getSystemInfo());
				rfcError.setSiges(sigesService.findByKey("codeSiges", rfc.getCodeProyect()));
				rfcError.setError(error);
				RFC_WithoutRelease rfcWithRelease = rfcService.findRfcWithRelease(rfc.getId());
				rfcError.setRfc(rfcWithRelease);
				rfcError.setObservations(newMotive);
				Timestamp dateFormat = CommonUtils.getSystemTimestamp();
				rfcError.setErrorDate(dateFormat);
				node.getStatus().setReason(newMotive);
				rfc.setNode(node);
				rfc.setStatus(node.getStatus());
				rfc.setOperator(getUserLogin().getFullName());
				rfcErrorService.save(rfcError);
				rfc.setOperator(getUserLogin().getFullName());
				rfc.setMotive(motive);
				rfc.setRequestDate(dateFormat);

				wfrfcService.wfStatusRFCWithOutMin(rfc);
				StatusRFC statusChange = statusRFCService.findByKey("name", "Borrador");
				rfc.setStatus(statusChange);

				if (statusChange != null && statusChange.getName().equals("Borrador")) {
					Set<Release_RFCFast> releases = rfc.getReleases();
					for (Release_RFCFast release : releases) {
						release.setStatus(release.getStatusBefore());
						release.setMotive("Devuelto al estado " + release.getStatus().getName());
						releaseService.updateStatusReleaseRFC(release, user);
					}
				}
				newMotive = "Paso a borrador por " + error.getName();

				node.getStatus().setReason(newMotive);
				rfc.setNode(node);
			}
			wfrfcService.wfStatusRFC(rfc);

			// Si esta marcado para enviar correo
			if (node.getSendEmail()) {
				Integer idTemplate = Integer.parseInt(paramService.findByCode(29).getParamValue());
				EmailTemplate email = emailService.findById(idTemplate);
				WFRFC rfcEmail = rfc;
				Thread newThread = new Thread(() -> {
					try {
						emailService.sendMailRFC(rfcEmail, email, motive);
					} catch (Exception e) {
						Sentry.capture(e, "release");
					}
				});
				newThread.start();
			}

			// si tiene un nodo y ademas tiene actor se notifica por correo
			if (node != null && node.getActors().size() > 0) {
				Integer idTemplate = Integer.parseInt(paramService.findByCode(27).getParamValue());
				EmailTemplate emailActor = emailService.findById(idTemplate);
				WFRFC rfcEmail = rfc;
				Thread newThread = new Thread(() -> {
					try {
						emailService.sendMailActorRFC(rfcEmail, emailActor);
					} catch (Exception e) {
						Sentry.capture(e, "release");
					}

				});
				newThread.start();
			}

			res.setStatus("success");
		} catch (Exception e) {
			Sentry.capture(e, "wfReleaseManagement");
			res.setStatus("exception");
			res.setException("Error al cambiar estado del release: " + e.getMessage());
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
		}
		return res;
	}
	/*
	 * 
	 * @RequestMapping(value = "/wfStatus", method = RequestMethod.POST)
	 * public @ResponseBody JsonResponse draftRelease(HttpServletRequest request,
	 * Model model,
	 * 
	 * @RequestParam(value = "idRelease", required = true) Integer idRelease,
	 * 
	 * @RequestParam(value = "idNode", required = true) Integer idNode,
	 * 
	 * @RequestParam(value = "motive", required = true) String motive) {
	 * JsonResponse res = new JsonResponse(); try { WFRelease release =
	 * wfReleaseService.findWFReleaseById(idRelease); Node node =
	 * nodeService.findById(idNode); node.getStatus().setMotive(motive);
	 * release.setNode(node); release.setStatus(node.getStatus());
	 * release.setOperator(getUserLogin().getFullName());
	 * wfReleaseService.wfStatusRelease(release);
	 * 
	 * // Si esta marcado para enviar correo if (node.getSendEmail()) { Integer
	 * idTemplate = Integer.parseInt(paramService.findByCode(21).getParamValue());
	 * EmailTemplate email = emailService.findById(idTemplate); WFRelease
	 * releaseEmail = release; Thread newThread = new Thread(() -> { try {
	 * emailService.sendMail(releaseEmail, email, motive); } catch (Exception e) {
	 * Sentry.capture(e, "release"); } }); newThread.start(); }
	 * 
	 * // si tiene un nodo y ademas tiene actor se notifica por correo if (node !=
	 * null && node.getActors().size() > 0) { Integer idTemplate =
	 * Integer.parseInt(paramService.findByCode(22).getParamValue()); EmailTemplate
	 * emailActor = emailService.findById(idTemplate); WFRelease releaseEmail =
	 * release; Thread newThread = new Thread(() -> { try {
	 * emailService.sendMailActor(releaseEmail, emailActor); } catch (Exception e) {
	 * Sentry.capture(e, "release"); }
	 * 
	 * }); newThread.start(); }
	 * 
	 * res.setStatus("success"); } catch (Exception e) { Sentry.capture(e,
	 * "wfReleaseManager"); res.setStatus("exception");
	 * res.setException("Error al cambiar estado del release: " + e.getMessage());
	 * logger.log(MyLevel.RELEASE_ERROR, e.toString()); } return res; }
	 */

	public void loadCountsRFC(HttpServletRequest request, Object[] systemIds) {
		Map<String, Integer> wfCount = new HashMap<String, Integer>();
		wfCount.put("start", wfrfcService.countByType("start", systemIds));
		wfCount.put("action", wfrfcService.countByType("action", systemIds));
		wfCount.put("finish", wfrfcService.countByType("finish", systemIds));
		wfCount.put("all", (wfCount.get("start") + wfCount.get("action") + wfCount.get("finish")));
		request.setAttribute("wfCount", wfCount);
	}

	public void loadCountsRelease(HttpServletRequest request, Object[] systemIds, Integer idUser) {
		Map<String, Integer> wfCount = new HashMap<String, Integer>();
		wfCount.put("start", wfReleaseService.countByType("start", systemIds, idUser));
		wfCount.put("action", wfReleaseService.countByType("action", systemIds, idUser));
		wfCount.put("finish", wfReleaseService.countByType("finish", systemIds, idUser));
		wfCount.put("all", (wfCount.get("start") + wfCount.get("action") + wfCount.get("finish")));
		request.setAttribute("wfCount", wfCount);
	}
}
