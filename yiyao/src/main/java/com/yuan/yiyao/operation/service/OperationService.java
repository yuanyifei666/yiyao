package com.yuan.yiyao.operation.service;

import com.yuan.yiyao.operation.dto.OperationDTO;
import com.yuan.yiyao.operation.vo.Operation;

import java.util.List;

public interface OperationService {
    List<OperationDTO> findByParentId(Integer parent_id,Integer code);

    void save(Operation operation );

    void updateOperationById(Operation operation);

    void deleteOperations(String ids);

    Operation findOperationByCode(String code);

    List<OperationDTO> findOperationByZtree();

}
