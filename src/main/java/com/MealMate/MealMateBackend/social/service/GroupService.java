package com.MealMate.MealMateBackend.social.service;

import com.MealMate.MealMateBackend.social.dto.GroupDTO;
import com.MealMate.MealMateBackend.social.dto.GroupCreateDTO;
import java.util.List;

public interface GroupService {
    List<GroupDTO> getAllGroups();
    GroupDTO getGroupById(Long id);
    GroupDTO createGroup(GroupCreateDTO groupCreateDTO);
    GroupDTO updateGroup(Long id, GroupDTO groupDTO);
    void deleteGroup(Long id);
}