package com.example.demo.badge;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(path = "api/v1/Badge")
public class BadgeController {
    private final BadgeService badgeService;
    @Autowired
    public BadgeController(BadgeService badgeService) {
        this.badgeService = badgeService;
    }

    @GetMapping
    public List<Badge> getBadges(){
        return badgeService.getBadges();
    }


    @PostMapping(consumes = "multipart/form-data")
//    public byte[] registerBadge(@RequestParam("image") MultipartFile image, @ModelAttribute Badge badge) throws IOException {
//        return badgeService.addNewBadge(image , badge);
//    }
    public ResponseEntity<AddBadgeResponse> registerBadge(@RequestParam("image") MultipartFile image, @ModelAttribute Badge badge) throws IOException {
        return badgeService.addNewBadge(image , badge);
    }

    @DeleteMapping(path = "{badgeId}")
    public void deleteBadge(@PathVariable("badgeId") Long badgeId){
        badgeService.deleteBadge(badgeId);
    }
    @PutMapping(path = "{badgeId}")
    public void updateBadge(@PathVariable("badgeId") Long badgeId,
                              @RequestBody(required = false) Badge badge){
        badgeService.updateBadge(badgeId, badge);

    }
}
