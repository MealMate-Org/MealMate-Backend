package com.MealMate.MealMateBackend.social.controller;

import com.MealMate.MealMateBackend.social.dto.GroupMemberDTO;
import com.MealMate.MealMateBackend.social.service.GroupMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group-members")
public class GroupMemberController {

    @Autowired
    private GroupMemberService groupMemberService;

    @GetMapping
    public ResponseEntity<List<GroupMemberDTO>> getAllGroupMembers() {
        return ResponseEntity.ok(groupMemberService.getAllGroupMembers());
    }

    @GetMapping("/{groupId}/{userId}")
    public ResponseEntity<GroupMemberDTO> getGroupMemberById(@PathVariable Long groupId, @PathVariable Long userId) {
        return ResponseEntity.ok(groupMemberService.getGroupMemberById(groupId, userId));
    }

    @PostMapping
    public ResponseEntity<GroupMemberDTO> createGroupMember(@RequestBody GroupMemberDTO groupMemberDTO) {
        return ResponseEntity.ok(groupMemberService.createGroupMember(groupMemberDTO));
    }

    @PutMapping("/{groupId}/{userId}")
    public ResponseEntity<GroupMemberDTO> updateGroupMember(@PathVariable Long groupId, @PathVariable Long userId, @RequestBody GroupMemberDTO groupMemberDTO) {
        return ResponseEntity.ok(groupMemberService.updateGroupMember(groupId, userId, groupMemberDTO));
    }

    @DeleteMapping("/{groupId}/{userId}")
    public ResponseEntity<Void> deleteGroupMember(@PathVariable Long groupId, @PathVariable Long userId) {
        groupMemberService.deleteGroupMember(groupId, userId);
        return ResponseEntity.noContent().build();
    }
}