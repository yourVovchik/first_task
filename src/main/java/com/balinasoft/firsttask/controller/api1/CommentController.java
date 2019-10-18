package com.balinasoft.firsttask.controller.api1;

import com.balinasoft.firsttask.dto.CommentDtoIn;
import com.balinasoft.firsttask.dto.CommentDtoOut;
import com.balinasoft.firsttask.dto.ImageDtoOut;
import com.balinasoft.firsttask.dto.ResponseDto;
import com.balinasoft.firsttask.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.balinasoft.firsttask.system.StaticWrapper.wrap;

@RestController
@RequestMapping("/api/image")
@Api(tags = "Comments")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "{imageId}/comment", method = RequestMethod.GET)
    @ApiOperation(value = "Get comments", response = ImageDtoOut.class)
    public ResponseDto getComments(@PathVariable int imageId, @RequestParam int page) {
        return wrap(commentService.get(imageId, page));
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "{imageId}/comment", method = RequestMethod.POST)
    @ApiOperation(value = "Add comment", response = CommentDtoOut.class)
    public ResponseDto addComment(@PathVariable int imageId, @RequestBody @Valid CommentDtoIn commentDtoIn) {
        return wrap(commentService.add(imageId, commentDtoIn));
    }

    @Secured("ROLE_USER")
    @RequestMapping(value = "{imageId}/comment/{commentId}", method = RequestMethod.DELETE)
    @ApiOperation(value = "Delete comment", response = CommentDtoOut.class)
    public ResponseDto deleteComment(@PathVariable int imageId, @PathVariable int commentId) {
        commentService.delete(imageId, commentId);
        return wrap();
    }
}
