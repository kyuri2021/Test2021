package kr.co.softsoldesk.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import kr.co.softsoldesk.beans.DataBean1;

@Controller
public class TestController {
	
	  //test1의 매핑을 찾아서 응답결과 반환하는 메소드 실행
	  
	  @GetMapping("/test1") 
	  public String test1(HttpServletRequest request)
	  {//request 생성 
	   request.setAttribute("data1", "문자열1"); 
	   return "redirect:/result1"; //새로운 요청 정보에 의해 request소멸 ->null값 반환
	  
	  } //result1를 전달받아 매핑되는 메소드 실행 //redirect시 기존의 setAttribute값음 소멸되고 새롭게 요청하므로
	  // 반환값 없이 넘어옴 //forward시 반환값이 유지되어 넘어옴
	  
	  @GetMapping("/result1") 
	  public String result1(HttpServletRequest request) {
	  //redirect시 응답 결과가 반환되고 나서 request객체는 소멸되므로 반환되는 값이 없어서 null값이 출력됨 forward는
	  //브라우져에서 생성된것이 아니므로 결과가 반환되고 나면 다시 result1로 전달되고 최종 결과값으로 반환됨 
	  String data1 =(String)request.getAttribute("data1"); 
	  System.out.printf("data1 : %s\n", data1);
	  
	  return "result1"; }
	 

//============================================================

	/*@GetMapping("/test1")
	public String test1(HttpServletRequest request) {
		request.setAttribute("data1", "문자열1");
		return "forward:/result1";
	}

	// forward시 반환값이 유지되어 넘어옴
	@GetMapping("/result1")
	public String result1(HttpServletRequest request) {
		String data1 = (String) request.getAttribute("data1");
		System.out.printf("data1 : %s\n", data1);
		return "result1";
	}*/

	// =====================================================
	@GetMapping("/test2")
	public String test2(Model model) {
		// model 사용시 request로 저장
		model.addAttribute("data2", "문자열2");
		return "forward:/result2";
	}

	/*
	 * @GetMapping("/result2") //Model에서 Model로 request를 전달 받을 수 없음 public String
	 * result2(Model model) { 
	 * String data2 = (String)model.getAttribute("data2");
	 * System.out.printf("data2 : %s\n", data2); //null
	 * 
	 * return "result2"; }
	 */

	@GetMapping("/result2")
	public String result2(HttpServletRequest request) {
		String data2 = (String) request.getAttribute("data2");
		System.out.printf("data2 : %s\n", data2);

		return "result2";
	}

	// =====================================================
	@GetMapping("/test3")
	public ModelAndView test3(ModelAndView mv) {

		mv.addObject("data3", "문자열3");
		mv.setViewName("forward:/result3");
		return mv;
	}

	@GetMapping("/result3")
	public String result3(HttpServletRequest request) {
		String data3 = (String) request.getAttribute("data3");
		System.out.printf("data3 : %s\n", data3);

		return "result3";
	}

	// ========================================================
	@GetMapping("/test4")
	public String test4(Model model) {
		DataBean1 bean1 = new DataBean1();
		bean1.setData1("문자열4");
		bean1.setData2("문자열5");
		// request에 저장
		model.addAttribute("bean1", bean1);

		return "forward:/result4";// request에 data유지 되면서 넘어감
	}

	@GetMapping("/result4")
	// request로 받음
	public String result4(HttpServletRequest request) {

		DataBean1 bean1 = (DataBean1) request.getAttribute("bean1");
		System.out.printf("bean1.data1 : %s\n", bean1.getData1());
		System.out.printf("bean1.data2 : %s\n", bean1.getData2());

		return "result4";
	}

	// ===========================================================
	@GetMapping("/test5")
	// bean1 이름을 부여해서 주입
	public String test5(@ModelAttribute("bean1") DataBean1 bean1) {
		bean1.setData1("문자열6");
		bean1.setData2("문자열7");
		// return "test5";
		return "forward:/result5";
	}
	// ==========================
	/*
	 * @GetMapping("/result5") //새로운 객체 주입으로 인하여 request에 저장된 데이터를 받아오지 못함 null값 출력
	 * public String result5(@ModelAttribute("bean1") DataBean1 bean1) {
	 * System.out.printf("bean1.data1 : %s\n", bean1.getData1());
	 * System.out.printf("bean1.data2 : %s\n", bean1.getData2());
	 * 
	 * return "result5"; }
	 */

	@GetMapping("/result5")
	public String result5(HttpServletRequest request) {
		// request로 주입받은후 DataBean1에서 데이터를 가져오기 위해 객체생성
		DataBean1 bean1 = (DataBean1) request.getAttribute("bean1");
		System.out.printf("bean1.data1 : %s\n", bean1.getData1());
		System.out.printf("bean1.data2 : %s\n", bean1.getData2());

		return "result5";
	}

}
