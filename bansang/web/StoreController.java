package org.bansang.web;

import org.bansang.dto.RecommendDTO;
import org.bansang.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gargoylesoftware.htmlunit.javascript.host.Console;

import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@CrossOrigin(origins="*", allowedHeaders="*")
@RestController
@RequestMapping("/store/*")
@Log
public class StoreController {

	@Autowired
	private StoreService storeService;

	@GetMapping("/list")
    public List<RecommendDTO> list(RecommendDTO dto){
		return storeService.list(dto);
	}
	
	@GetMapping("/listMap")
    public List<RecommendDTO> listMap(RecommendDTO dto){
		return storeService.listMap(dto);
	}
	
	@GetMapping("/specificList")
    public List<RecommendDTO> specificList(RecommendDTO dto){
		return storeService.specificList(dto);
	}
	
    @GetMapping("/specificListMap")
    public List<RecommendDTO> specificListMap(RecommendDTO dto){
		return storeService.specificListMap(dto);
	}
	

    @GetMapping("/view")
    public RecommendDTO getView(Long storeNumber) {

        return storeService.view(storeNumber);
    }

    @PostMapping("/recommend")
    public void addValue(@RequestBody RecommendDTO dto){
        storeService.register(dto);
    }

    @GetMapping("/{storeNum}")
    public RecommendDTO getRecommendList(@RequestParam("storeNum") Long storeNum) {
    	
        return storeService.getInfo(storeNum);
    }
    
    @GetMapping("/images/{storeNum}")
    public List<String> getCrawlingImgList(@PathVariable("storeNum") Long storeNum) {
    
    	return storeService.getImageList(storeNum);
    }

    @GetMapping(value="/blog/{area}", produces="text/json;charset=UTF-8")
    public String blogList(@PathVariable("area") String area) {
        
        String clientId = "JU1ZHvkqIuJ2itqjbi6v";
        String clientSecret = "w55QlDJ26S";
        
        try {
            String text = URLEncoder.encode(area + " 맛집", "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text; 
            // String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query="+ text; 
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if (responseCode == 200) {
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            log.info("" + response.toString());
            log.info("" + response);
            
            return response.toString();

        } catch (Exception e) {
            log.info("" + e);
            return null;
        }
    }

}