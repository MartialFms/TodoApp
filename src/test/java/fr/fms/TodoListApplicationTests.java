package fr.fms;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import fr.fms.business.IBusinessImpl;

@SpringBootTest
class TodoListApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	IBusinessImpl businessImpl;

	@Autowired
	private MockMvc mvc;

	@Test
	public void test_get_welcome() throws Exception {

		this.mvc.perform(get("/")).andExpect(status().isOk())
				.andExpect((ResultMatcher) content().string(containsString("Hello")));
	}

	@Test
	public void test_encoder() throws Exception {
		String expected = "fms2022";
		String result = businessImpl.encodePassword(expected);
		result.contains("$2a$");
	}

	@Test
	public void test_Auth() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		assertEquals(auth, null);
	}

}
