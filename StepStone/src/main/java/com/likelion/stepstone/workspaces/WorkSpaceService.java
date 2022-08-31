package com.likelion.stepstone.workspaces;



import com.likelion.stepstone.Util.DataNotFoundException;
import com.likelion.stepstone.workspaces.model.WorkSpaceDto;
import com.likelion.stepstone.workspaces.model.WorkSpaceEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;


public class WorkSpaceService {
    public final WorkSpaceRepository workspaceRepository;

    public WorkSpaceService(WorkSpaceRepository workspaceRepository) {
        this.workspaceRepository = workspaceRepository;
    }
    public void create(WorkSpaceDto workSpaceDto) {
        WorkSpaceEntity workSpaceEntity = WorkSpaceEntity.toEntity(workSpaceDto);
        workSpaceEntity.setTitle(workSpaceDto.getTitle());
        workSpaceEntity.setBody(workSpaceDto.getBody());
        workSpaceEntity.setCreatedAt(LocalDateTime.now());



        workspaceRepository.save(workSpaceEntity);
    }

    public Page<WorkSpaceEntity> getList(int page) {
        Pageable pageable = PageRequest.of(page, 5); // 한 페이지에 10까지 가능
        return workspaceRepository.findAll(pageable);
    }

public WorkSpaceEntity getWorkSpaceEntity(Long workspaceCid) {
    return workspaceRepository.findByWorkspaceCid(workspaceCid)
            .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(workspaceCid)));
}

    public void delete(WorkSpaceEntity workSpaceEntity) {
        workspaceRepository.delete(workSpaceEntity);
    }

    public void modify(WorkSpaceEntity workSpaceEntity, String title, String body) {
        workSpaceEntity.setTitle(title);
        workSpaceEntity.setBody(body);
        workSpaceEntity.setUpdatedAt(LocalDateTime.now());
        workspaceRepository.save(workSpaceEntity);
    }

}