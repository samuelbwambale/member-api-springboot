package com.example.memberapi.controller;

import com.example.memberapi.entities.Member;
import com.example.memberapi.model.MemberJSONResponse;
import com.example.memberapi.service.IMemberService;
import com.example.memberapi.util.ObjectToJsonUtil;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IMemberService memberService;

    MockHttpServletResponse mockResponse;
    Gson gson;

    Member member1 = new Member(1L, "John", "Joe", LocalDate.parse("2022-02-10"), LocalDate.parse("2022-02-11"));
    Member member2 = new Member(2L, "Bill", "Smith", LocalDate.parse("2022-02-11"), LocalDate.parse("2022-02-11"));
    List<Member> memberList = List.of(member1, member2);

    @Test
    public void testGetMemberById() throws Exception {
        when(memberService.getMemberById(1L)).thenReturn(Optional.of(memberList.get(1)));

        mockMvc.perform(MockMvcRequestBuilders.get("/member").param("memberId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Bill"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.lastUpdatedTs").value("2022-02-11"));
    }

    @Test
    public void testGetMemberByIdUsingGson() throws Exception {
        when(memberService.getMemberById(1L)).thenReturn(Optional.of(memberList.get(1)));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/member")
                .param("memberId", "1")).andReturn();

        mockResponse = result.getResponse();
        assertEquals(HttpStatus.OK.value(), mockResponse.getStatus());

        String member = mockResponse.getContentAsString();
        gson = new Gson();
        MemberJSONResponse memberJSONResponse = gson.fromJson(member, MemberJSONResponse.class);
        assertEquals(memberJSONResponse.getFirstName(), memberList.get(1).getFirstName());
    }

    @Test
    public void testGetMemberByIdWithNonExistingId() throws Exception {
        when(memberService.getMemberById(9L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/member").param("memberId", "1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddNewMember() throws Exception {
        Member member = new Member(null, "Alex", "Robert", null, null);
        when(memberService.addMember(member)).thenReturn(memberList.get(0));

        mockMvc.perform(MockMvcRequestBuilders.post("/member")
                .content(ObjectToJsonUtil.asJsonString(member))
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddNewMemberWithInvalidRequestBody() throws Exception {
        Member member = new Member(null, "", "John", null, null);
        System.out.println(ObjectToJsonUtil.asJsonString(member));
        mockMvc.perform(MockMvcRequestBuilders.post("/member")
                        .content(ObjectToJsonUtil.asJsonString(member))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateMemberByOverwritingExistingRecord() throws Exception {
        Member member = new Member(null, "Alex", "Robert", null, null);
        Member updatedMember = new Member(1L, "Alex", "Robert", LocalDate.parse("2022-02-10"), LocalDate.now());
        when(memberService.updateMember(1L, member)).thenReturn(updatedMember);

        mockMvc.perform(MockMvcRequestBuilders.put("/member/{memberId}", 1L)
                        .content(ObjectToJsonUtil.asJsonString(member))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andExpect(jsonPath("$.lastUpdatedTs").value(LocalDate.now().toString()));
    }

    @Test
    public void testUpdateMemberForNonExistingRecord() throws Exception {
        Member member = new Member(null, "Alex", "Robert", null, null);
        Member updatedMember = new Member(9L, "Alex", "Robert", LocalDate.now(), LocalDate.now());
        when(memberService.updateMember(9L, member)).thenReturn(updatedMember);

        mockMvc.perform(MockMvcRequestBuilders.put("/member/{memberId}", 9L)
                        .content(ObjectToJsonUtil.asJsonString(member))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Alex"))
                .andExpect(jsonPath("$.createTs").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.lastUpdatedTs").value(LocalDate.now().toString()));
    }

    @Test
    public void testDeleteMember() throws Exception {
        doNothing().when(memberService).deleteMember(12L);
        mockMvc.perform(MockMvcRequestBuilders.delete("/member/")
                        .param("memberId", "12"))
                .andExpect(status().isNotFound());
    }
}
