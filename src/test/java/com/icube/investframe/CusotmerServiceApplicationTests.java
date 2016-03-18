package com.icube.investframe;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import com.icube.investframe.entity.Account;
import com.icube.investframe.entity.User;
/**
 * 
 * @author aimbastari
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CusotmerServiceApplication.class)
@WebAppConfiguration
public class CusotmerServiceApplicationTests {
	
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	private MockMvc mockMvc;
	
	private Long userId = new Long(1);
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;
	
	private User user;
	
	private List<Account> accounts =  new ArrayList<Account>();
	
	@Autowired
	private UserRepository urepo;
	
	@Autowired
	private AccountRepository arepo;
	
    @Autowired
    private WebApplicationContext webApplicationContext;
	
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.notNull(this.mappingJackson2HttpMessageConverter,
        		"the JSON message converter must not be null");
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.arepo.deleteAllInBatch();
        this.urepo.deleteAllInBatch();

        this.user = urepo.save(new User("alex", "imbastari", "aimbastari", "1234"));
        this.accounts.add(arepo.save(new Account("account 1", user)));
        this.accounts.add(arepo.save(new Account("account 2", user)));
    }
    
    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(get("/users/123213"))
                .andExpect(status().isNotFound());
    }
	
    @Test
    public void userFound() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }
    
/*    
    @Test
    public void readSingleAccount() throws Exception {
        mockMvc.perform(get("/users/" + userId + "/accounts"))
                + this.accounts.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.accounts.get(0).getId().intValue())))
                .andExpect(jsonPath("$.name", is("account 1")));
    }
*/    
    
    
    @Test
    public void createAccount() throws Exception {
        String accountJson = json(new Account("new account 3", this.user));
        mockMvc.perform( MockMvcRequestBuilders.post("/users/1/accounts")
                .contentType(contentType)
                .content(accountJson))
                .andExpect(status().isCreated());
    }

    
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
    
    
}
