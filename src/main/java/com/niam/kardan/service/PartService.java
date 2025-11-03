package com.niam.kardan.service;

import com.niam.common.exception.EntityNotFoundException;
import com.niam.common.exception.ResultResponseStatus;
import com.niam.common.utils.MessageUtil;
import com.niam.kardan.model.Part;
import com.niam.kardan.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PartService {
    private final PartRepository partRepository;
    private final MessageUtil messageUtil;

    public Part savePart(Part part) {
        return partRepository.save(part);
    }

    public Part updatePart(Long id, Part updatedPart) {
        Part existingPart = getPart(id);
        return partRepository.save(existingPart);
    }

    public void deletePart(Long id) {
        Part part = getPart(id);
        partRepository.delete(part);
    }

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Part getPart(Long id) {
        return partRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                ResultResponseStatus.ENTITY_NOT_FOUND.getResponseCode(),
                                ResultResponseStatus.ENTITY_NOT_FOUND.getReasonCode(),
                                messageUtil.getMessage(ResultResponseStatus.ENTITY_NOT_FOUND.getDescription(),
                                        "Part")));
    }
}