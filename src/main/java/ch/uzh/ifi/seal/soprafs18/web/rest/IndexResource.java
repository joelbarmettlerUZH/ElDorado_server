package ch.uzh.ifi.seal.soprafs18.web.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexResource {

	@RequestMapping(value="/")
	@ResponseBody
	public String index() {
		return "SoPra 2018 has started! (Group 17)";
	}
}
