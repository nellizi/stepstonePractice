package com.likelion.stepstone.workspaces;

import com.likelion.stepstone.Util.DataNotFoundException;
import com.likelion.stepstone.authentication.PrincipalDetails;
import com.likelion.stepstone.user.model.UserEntity;
import com.likelion.stepstone.workspaces.model.WorkSpaceDto;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public class WorkSpaceService {
    public final WorkSpaceRepository workspaceRepository;
    public WorkSpaceService(WorkSpaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }
    public void create(WorkSpaceDto workSpaceDto, PrincipalDetails principalDetails) {
        WorkSpaceEntity workSpaceEntity = WorkSpaceEntity.toEntity(workSpaceDto);
        UserEntity userEntity = principalDetails.getUser();

        workSpaceEntity.setTitle(workSpaceDto.getTitle());
        workSpaceEntity.setBody(workSpaceDto.getBody());
        workSpaceEntity.setCreatedAt(LocalDateTime.now());
        workSpaceEntity.setUser(userEntity);

        workspaceRepository.save(workSpaceEntity);
    }
    public Page<WorkSpaceEntity> getList(int page) {
        Pageable pageable = getPageable(page, 5, Sort.by(Sort.Direction.DESC, "workspaceCid"));
        return workspaceRepository.findAll(pageable);
    }
    public Page<WorkSpaceEntity> getListWithId(long id, int page) {
        Pageable pageable = getPageable(page, 5, Sort.by(Sort.Direction.DESC, "workspaceCid"));
        return workspaceRepository.findAllByPostCid(id, pageable);
    }

    private Pageable getPageable(int page, int size, Sort DESC) {
        Pageable pageable = PageRequest.of(page, size, DESC);
        return pageable;
    }

    public WorkSpaceDto getWorkSpaceDto(Long workspaceCid) {
        WorkSpaceEntity workSpaceEntity =  workspaceRepository.findByWorkspaceCid(workspaceCid)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(workspaceCid)));
        WorkSpaceDto workSpaceDto = WorkSpaceDto.toDto(workSpaceEntity);

        return workSpaceDto;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #workSpaceDto.user.userId == authentication.principal.username")
    @Transactional
    public void delete(WorkSpaceDto workSpaceDto) {
        Long workspaceCid = workSpaceDto.getWorkspaceCid();
        workspaceRepository.deleteByWorkspaceCid(workspaceCid);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #workSpaceDto.user.userId == authentication.principal.username")
    public void modify(WorkSpaceDto workSpaceDto, String title, String body) {

        Optional<WorkSpaceEntity> workSpaceEntities =  workspaceRepository.findByWorkspaceCid(workSpaceDto.getWorkspaceCid());
        WorkSpaceEntity workSpaceEntity = workSpaceEntities.get();

        workSpaceEntity.setTitle(title);
        workSpaceEntity.setBody(body);
        workSpaceEntity.setUpdatedAt(LocalDateTime.now());

        workspaceRepository.save(workSpaceEntity);

    }

    public Page<WorkSpaceEntity> getMyWorkPostList(int page, Long userCid) {
        Pageable pageable = getPageable(page, 3, Sort.by(Sort.Direction.DESC, "workspaceCid"));
        return workspaceRepository.findAllByUserUserCid(userCid, pageable);
    }

    public WorkSpaceDto getWorkspaceDto(long id) {
        WorkSpaceEntity workSpaceEntity = workspaceRepository.findByWorkspaceCid(id);
        WorkSpaceDto workSpaceDto = WorkSpaceDto.toDto(workSpaceEntity);

        return workSpaceDto;
    }
}