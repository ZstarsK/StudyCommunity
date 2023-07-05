package com.sc.controller;

import com.sc.service.CommentService;
import com.sc.service.impl.CommentServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "CommentController")
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
}
