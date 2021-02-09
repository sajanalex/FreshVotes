package com.freshvotes.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freshvotes.domain.Feature;
import com.freshvotes.domain.User;
import com.freshvotes.service.FeatureService;

@Controller
@RequestMapping("/products/{productId}/features")
public class FeatureController {
	Logger log = LoggerFactory.getLogger(FeatureController.class);
	@Autowired
	FeatureService featureService;
	
	@PostMapping("") // Maps to link pointed above the class declaration
	public String createFeature(@AuthenticationPrincipal User user,@PathVariable Long productId) {
		Feature feature= featureService.createFeature(productId,user);
		
		return "redirect:/products/"+productId+"/features/"+feature.getId();
	}
	
	@GetMapping("{featureId}") // From request mapping it prepend  /products/{productId}/features
	public String getFeature(@AuthenticationPrincipal User user,ModelMap model, @PathVariable Long productId,@PathVariable Long featureId) {
		Optional<Feature> featureOpt = featureService.findById(featureId);
		
		featureOpt.ifPresent((Feature f)->{model.put("feature", featureOpt.get());
		model.put("comments", featureOpt.get().getComments());});
		model.put("user", user);
				
		return "feature";
	}
	
	@PostMapping("{featureId}")
	public String updateFeature(@AuthenticationPrincipal User user,Feature feature, @PathVariable Long productId,@PathVariable Long featureId) {
		feature.setUser(user);
		feature = featureService.save(feature);
		String encodedproductName;
		//return "redirect:/products/"+productId+"/features/"+feature.getId();
		try {
		encodedproductName	= ""+URLEncoder.encode(feature.getProduct().getName(), "UTF-8");
			
		} catch (UnsupportedEncodingException e) {
			log.warn("Unable to encode"+feature.getProduct().getName()+" redirecting to dashboard");
			return "redirect:/dashboard";
		}
		return "redirect:/p/"+encodedproductName;
	}

	
}
