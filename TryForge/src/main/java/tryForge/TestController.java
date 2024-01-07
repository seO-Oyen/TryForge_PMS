package tryForge;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	@Autowired(required = false)
	private TestService service;
	
	@RequestMapping("test.do")
	public String boardList() {
		return "test";
	}
}
