package com.soin.sgrm.controller.admin;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.soin.sgrm.controller.BaseController;
import com.soin.sgrm.exception.Sentry;
import com.soin.sgrm.model.SystemConfiguration;
import com.soin.sgrm.model.SystemInfo;
import com.soin.sgrm.service.SystemConfigurationService;
import com.soin.sgrm.service.SystemService;
import com.soin.sgrm.utils.JsonResponse;
import com.soin.sgrm.utils.MyLevel;

@Controller
@RequestMapping("/admin/systemConfig")
public class SystemConfigurationController extends BaseController {
	
	public static final Logger logger = Logger.getLogger(SystemConfigurationController.class);

	@Autowired
	private SystemConfigurationService systemConfigurationService;
	@Autowired
	SystemService systemService;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, Locale locale, Model model, HttpSession session) {
		model.addAttribute("listSystemConfig", systemConfigurationService.list());
		model.addAttribute("systemConfig", new SystemConfiguration());
		model.addAttribute("systems", systemService.listAll());
		model.addAttribute("system", new SystemInfo());
		return "/admin/systemConfig/systemConfig";
	}

	@RequestMapping(value = "/findSystemConfig/{id}", method = RequestMethod.GET)
	public @ResponseBody SystemConfiguration findSystemConfig(@PathVariable Integer id, HttpServletRequest request,
			Locale locale, Model model, HttpSession session) {
		try {
			SystemConfiguration systemConfig = systemConfigurationService.findById(id);
			return systemConfig;
		} catch (Exception e) {
			Sentry.capture(e, "systemConfig");
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
			return null;
		}
	}

	@RequestMapping(value = "/updateSystemConfig", method = RequestMethod.POST)
	public @ResponseBody JsonResponse updateSystemConfig(HttpServletRequest request,
			@ModelAttribute("SystemConfiguration") SystemConfiguration sys, ModelMap model, Locale locale,
			HttpSession session) {
		JsonResponse res = new JsonResponse();
		try {
			res.setStatus("success");
			SystemConfiguration systemConfig = systemConfigurationService.findById(sys.getId());
			systemConfig.setObservations(sys.getObservations());
			systemConfig.setSolutionInfo(sys.getSolutionInfo());
			systemConfig.setDefinitionEnvironment(sys.getDefinitionEnvironment());
			systemConfig.setInstalationData(sys.getInstalationData());
			systemConfig.setDataBaseInstructions(sys.getDataBaseInstructions());
			systemConfig.setDownEnvironment(sys.getDownEnvironment());
			systemConfig.setEnvironmentObservations(sys.getEnvironmentObservations());
			systemConfig.setSuggestedTests(sys.getSuggestedTests());
			systemConfig.setConfigurationItems(sys.getConfigurationItems());
			systemConfig.setDependencies(sys.getDependencies());
			systemConfig.setAttachmentFiles(sys.getAttachmentFiles());
			systemConfig.setApplicationVersion(sys.isApplicationVersion());
			systemConfig = systemConfigurationService.update(systemConfig);
			res.setObj(systemConfig);
		} catch (Exception e) {
			Sentry.capture(e, "systemConfig");
			res.setStatus("exception");
			res.setException("Error al modificar configuración: " + e.toString());
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
		}
		return res;
	}

	@RequestMapping(value = "/createSystemConfig", method = RequestMethod.POST)
	public @ResponseBody JsonResponse createSystemConfig(HttpServletRequest request,
			@ModelAttribute("SystemConfiguration") SystemConfiguration sys, ModelMap model, Locale locale,
			HttpSession session) {
		JsonResponse res = new JsonResponse();
		try {
			res.setStatus("success");
			
			if(sys.getSystemId() == null) {
				res.setStatus("fail");
				res.addError("systemId", "Seleccione una opción");
				return res;
			}
			
			SystemConfiguration systemConfig = systemConfigurationService.findBySystemId(sys.getSystemId());

			if (systemConfig != null) {
				res.setStatus("fail");
				res.addError("systemId", "Ya existe una configuración para el sistema");
			} else {
				systemConfig = new SystemConfiguration();
				systemConfig.setObservations(sys.getObservations());
				systemConfig.setSolutionInfo(sys.getSolutionInfo());
				systemConfig.setDefinitionEnvironment(sys.getDefinitionEnvironment());
				systemConfig.setInstalationData(sys.getInstalationData());
				systemConfig.setDataBaseInstructions(sys.getDataBaseInstructions());
				systemConfig.setDownEnvironment(sys.getDownEnvironment());
				systemConfig.setEnvironmentObservations(sys.getEnvironmentObservations());
				systemConfig.setSuggestedTests(sys.getSuggestedTests());
				systemConfig.setConfigurationItems(sys.getConfigurationItems());
				systemConfig.setDependencies(sys.getDependencies());
				systemConfig.setAttachmentFiles(sys.getAttachmentFiles());
				systemConfig.setApplicationVersion(sys.isApplicationVersion());
				SystemInfo system = new SystemInfo();
				system.setId(sys.getSystemId());
				systemConfig.setSystem(system);
				systemConfigurationService.save(systemConfig);
			}
		} catch (Exception e) {
			Sentry.capture(e, "systemConfig");
			res.setStatus("exception");
			res.setException("Error al guardar configuración: " + e.toString());
			logger.log(MyLevel.RELEASE_ERROR, e.toString());
		}
		return res;
	}

}
