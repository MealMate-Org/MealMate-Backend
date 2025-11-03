package com.MealMate.MealMateBackend.social.service;

import com.MealMate.MealMateBackend.social.dto.GroupMemberDTO;
import com.MealMate.MealMateBackend.social.model.GroupMember;
import com.MealMate.MealMateBackend.social.repository.GroupMemberRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GroupMemberServiceImpl implements GroupMemberService {

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<GroupMemberDTO> getAllGroupMembers() {
        return groupMemberRepository.findAll().stream()
                .map(groupMember -> modelMapper.map(groupMember, GroupMemberDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public GroupMemberDTO getGroupMemberById(Long groupId, Long userId) {
        GroupMember groupMember = groupMemberRepository.findByGroupIdAndUserId(groupId, userId).orElseThrow(() -> new RuntimeException("GroupMember not found"));
        return modelMapper.map(groupMember, GroupMemberDTO.class);
    }

    @Override
    public GroupMemberDTO createGroupMember(GroupMemberDTO groupMemberDTO) {
        GroupMember groupMember = modelMapper.map(groupMemberDTO, GroupMember.class);
        GroupMember savedGroupMember = groupMemberRepository.save(groupMember);
        return modelMapper.map(savedGroupMember, GroupMemberDTO.class);
    }

    @Override
    public GroupMemberDTO updateGroupMember(Long groupId, Long userId, GroupMemberDTO groupMemberDTO) {
        GroupMember groupMember = groupMemberRepository.findByGroupIdAndUserId(groupId, userId).orElseThrow(() -> new RuntimeException("GroupMember not found"));
        modelMapper.map(groupMemberDTO, groupMember);
        GroupMember updatedGroupMember = groupMemberRepository.save(groupMember);
        return modelMapper.map(updatedGroupMember, GroupMemberDTO.class);
    }

    @Override
    public void deleteGroupMember(Long groupId, Long userId) {
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);
    }
}