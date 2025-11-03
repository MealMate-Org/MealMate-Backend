package com.MealMate.MealMateBackend.social.service;

import com.MealMate.MealMateBackend.social.dto.GroupMemberDTO;
import java.util.List;

public interface GroupMemberService {
    List<GroupMemberDTO> getAllGroupMembers();
    GroupMemberDTO getGroupMemberById(Long groupId, Long userId);
    GroupMemberDTO createGroupMember(GroupMemberDTO groupMemberDTO);
    GroupMemberDTO updateGroupMember(Long groupId, Long userId, GroupMemberDTO groupMemberDTO);
    void deleteGroupMember(Long groupId, Long userId);
}