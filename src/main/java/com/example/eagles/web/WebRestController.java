package com.example.eagles.web;

import com.example.eagles.newsbigdata.Bigkinds;
import com.example.eagles.newsbigdata.IssueRanking;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebRestController {

    @GetMapping("/hello")
    public String hello() {
        IssueRanking issueRanking = new IssueRanking();
        String hello = "hello world";
        String jsontest = issueRanking.makeIssue("2016-01-18").toString();
        return jsontest;
    }

    @GetMapping("/test")
    public String test() {
        IssueRanking issueRanking = new IssueRanking();
        Bigkinds bigkinds = new Bigkinds();
        String hello = "hello world";
        String posttest = issueRanking.makeIssue("2016-01-18").toString();
        String post = bigkinds.postURL("http://tools.kinds.or.kr:8888/issue_ranking",posttest);
        return post;
    }
}
