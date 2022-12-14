package stepDefinitions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.response.ResponseOptions;
import model.Posts;
import utilities.APIConstant;
import utilities.RestAssuredExtension;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;
import io.cucumber.datatable.DataTable;

public class stepDef {
	
	public static ResponseOptions<Response> response;
	public static RestAssuredExtension restAssuredExtention;
	public static int id;
	public static Posts postData = new Posts();;
	
	@Given("Perform GET operation to fetch all users for url {string}")
	public void perform_get_operation_to_fetch_all_users_for_url(String url) {
		restAssuredExtention = new RestAssuredExtension(url, APIConstant.ApiMethods.GET);
		response = restAssuredExtention.ExecuteAPI();
		ArrayList<Integer> users = response.getBody().jsonPath().get("id");
		assertThat(response.getStatusCode(), equalTo(200));
		System.out.println(users.size());
	}

	@When("Perform POST operation for {string} to create new user with body as")
	public void perform_post_operation_for_to_create_new_user_with_body_as(String url, DataTable dataTable) {
		List<List<String>> data = dataTable.asLists(String.class);
//		HashMap<String, String> body = new HashMap<>();
//      body.put("name", data.get(1).get(0));
//      body.put("gender", data.get(1).get(1));
//		body.put("email", data.get(1).get(2));
//      body.put("status", data.get(1).get(3));

		postData.setName(data.get(1).get(0));
		postData.setGender(data.get(1).get(1));
		postData.setEmail(data.get(1).get(2));
		postData.setStatus(data.get(1).get(3));
		
		restAssuredExtention = new RestAssuredExtension(url, APIConstant.ApiMethods.POST);
		response = restAssuredExtention.ExecuteWithBody(postData);
		System.out.println(response.getStatusCode());
		id = response.getBody().jsonPath().get("id");
		System.out.println(id);
		
	}
	
	@Then("Perform GET operation after creating new user to fetch all users for url {string}")
	public void perform_get_operation_after_creating_new_user_to_fetch_all_users_for_url(String url) {
		restAssuredExtention = new RestAssuredExtension(url, APIConstant.ApiMethods.GET);
		response = restAssuredExtention.ExecuteAPI();
//		ArrayList<Integer> users = response.getBody().jsonPath().get("id");
		assertThat(response.getStatusCode(), equalTo(200));
		System.out.println(response.getBody().jsonPath().getString("x[0].name"));
	}

	@Then("Perform GET operation on {string} to fetch newly created user")
	public void perform_get_operation_on_to_fetch_newly_created_user(String url) {
		restAssuredExtention = new RestAssuredExtension(url, APIConstant.ApiMethods.GET);
		String ID = String.valueOf(id);
		HashMap<String, String> pathParams = new HashMap<>();
		pathParams.put("id", ID);
		response = restAssuredExtention.ExecuteWithPathParams(pathParams);
		System.out.println(response.getBody().print());
	}

	@When("Perform PUT operation on {string} with body")
	public void perform_put_operation_on_with_body(String url, DataTable dataTable) {
		List<List<String>> data = dataTable.asLists(String.class);
		postData.setName(data.get(1).get(0));
		postData.setGender(data.get(1).get(1));
		postData.setEmail(data.get(1).get(2));
		postData.setStatus(data.get(1).get(3));

		
		String ID = String.valueOf(id);
		HashMap<String, String> pathParams = new HashMap<>();
		pathParams.put("id", ID);
		
		restAssuredExtention = new RestAssuredExtension(url, APIConstant.ApiMethods.PUT);
		response = restAssuredExtention.ExecuteWithPathParamsAndBody(pathParams, postData);
		System.out.println(response.getStatusCode());
		String name = response.getBody().jsonPath().get("name");
		assertThat(name, equalTo("Suresh123"));
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().jsonPath().get("name"));
		System.out.println(response.getBody().jsonPath().get("email"));	
	}

	@Then("Perform GET operation on {string} to get user details")
	public void perform_get_operation_on_to_get_user_details(String url) {
		restAssuredExtention = new RestAssuredExtension(url, APIConstant.ApiMethods.GET);
		String ID = String.valueOf(id);
		HashMap<String, String> pathParams = new HashMap<>();
		pathParams.put("id", ID);
		response = restAssuredExtention.ExecuteWithPathParams(pathParams);
		System.out.println(response.getBody().print());
		System.out.println(response.getStatusCode());
		System.out.println(response.getBody().jsonPath().getString("x[0].name"));
	}

	@Then("Perform DELETE operation on {string}")
	public void perform_delete_operation_on(String url) {
		restAssuredExtention = new RestAssuredExtension(url, APIConstant.ApiMethods.DELETE);
		String ID = String.valueOf(id);
		HashMap<String, String> pathParams = new HashMap<>();
		pathParams.put("id", ID);
		response = restAssuredExtention.ExecuteWithPathParams(pathParams);
		System.out.println(response.getBody().print());
		System.out.println(response.getStatusCode());
	}

	@Then("Perform GET operation to fetch deleted {string}")
	public void perform_get_operation_to_fetch_deleted(String url) {
		restAssuredExtention = new RestAssuredExtension(url, APIConstant.ApiMethods.GET);
		String ID = String.valueOf(id);
		HashMap<String, String> pathParams = new HashMap<>();
		pathParams.put("id", ID);
		response = restAssuredExtention.ExecuteWithPathParams(pathParams);
		System.out.println(response.getBody().print());
		System.out.println(response.getStatusCode());
	}
	


}
