package com.yuan.yiyao.operation.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yuan.yiyao.operation.dto.OperationDTO;
import com.yuan.yiyao.operation.repository.OperationRepository;
import com.yuan.yiyao.operation.service.OperationService;
import com.yuan.yiyao.operation.vo.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限管理的业务类
 */
@Service
@Transactional
public class OperationServiceImpl implements OperationService {

    @Autowired
    private OperationRepository repository;
    private Integer parent_id;
    /**
     * 根据父几点的id查询该节点下的子节点列表
     * @param parent_id
     * @return
     */
    public List<OperationDTO> findByParentId(Integer parent_id,Integer code) {
        List<Operation> list = null;
        this.parent_id = parent_id;

        //判断parent_id是否为空
        if (parent_id == null){
            if(code == 1){
                //查询parentid=999999
                list = repository.findByParentId(999999);
            }else{
                //查询所有根节点
                list = repository.findByRoot();
            }
        }else{
                //查询子节点列表
                list = repository.findByParentId(parent_id);
        }
        //把节点信息封装到OperationDTO对象中
        return toOperatoinDTO(list,code);
    }

    /**
     * OperationDTO的封装方法
     */
    public List<OperationDTO> toOperatoinDTO(List<Operation> list,Integer code){
        List<OperationDTO> result =  new ArrayList<>();
        String state = "";
        if (code == 1 && parent_id !=null){
            //得到该节点的父节点
            Operation parentNode =  repository.findById(parent_id);
            System.out.println("parentNode:"+parentNode);
            if (parentNode.getParentId() == 999999){
                state = "open";
            }
        }
        for (Operation operation: list) {
            Map<String,String> map = new HashMap<>();
            map.put("url",operation.getUrl());
            map.put("code",operation.getCode());
            OperationDTO resultDTO = new OperationDTO();
            resultDTO.setId(operation.getId());
            resultDTO.setText(operation.getName());
            resultDTO.setState(state == ""?operation.getState():state);
            resultDTO.setAttributes(map);
            resultDTO.setParentId(operation.getParentId());
            result.add(resultDTO);
        }
        return result;
    }

    /**
     * 添加新权限信息
     * @param operation 要添加的权限信息
     */
    public void save(Operation operation) {
        //更新父级权限的状态
        Operation parent = repository.selectById(operation.getParentId());
        System.out.println("parent:"+parent);
        //如果父权限的state为open则改为close
        if ("open".equals(parent.getState())){
            parent.setState("closed");
            //更新父级权限的信息
            repository.updateById(parent);
        }

        //完善权限信息
        operation.setState("open");
        repository.insert(operation);

    }

    /**
     * 更新权限的信息
     * @param operation
     */
    public void updateOperationById(Operation operation) {

        //得到该节点的父节点
        Operation beforeNode = repository.findById(operation.getId());
        Integer beforeParentId = beforeNode.getParentId();
        System.out.println("update-->"+beforeParentId);
        //更新权限节点信息
        repository.updateById(operation);
        //判断该节点下是否还有子节点
        List<Operation> chirlrens = repository.findByParentId(beforeParentId);
        if (chirlrens == null || chirlrens.size() == 0){
            //该父节点没有子节点了,把state改为open
            Operation parentNode = repository.findById(beforeParentId);
            System.out.println("update-->parentNode:"+parentNode);
            parentNode.setState("open");
            //更新节点的状态
            repository.updateById(parentNode);
        }

        //更新添加到新父节点的state
        Operation afterParentNode = repository.findById(operation.getParentId());
        if ("open".equals(afterParentNode.getState())){
            afterParentNode.setState("closed");
            //更新添加到新父节点的state
            repository.updateById(afterParentNode);
        }
    }

    /**
     * 批量删除系统权限信息
     * @param paramId
     */
    public void deleteOperations(String paramId) {
        //解析权限的id
        String ids[] = paramId.split(",");
        for (String id: ids) {
            Operation currentNode = repository.findById(Integer.parseInt(id));

            if (currentNode != null){
                //同步删除该节点下的所有子节点的信息
                deleteBatch(Integer.parseInt(id));
                //得到要删除节点的父节点
                Operation parentNode = repository.findById(currentNode.getParentId());
                //如果该节点的父节点只有当前节点，删除后要更新父节点的state为open
                List<Operation> chilrens = repository.findByParentId(parentNode.getId());

                System.out.println("chilrens-=------list:"+chilrens.size());
                //TODO 批量删除有bug，不能把父节点的state改为open
                //判断该该节点下是否还有子节点
                if (chilrens == null||chilrens.size()==0){
                    //把该节点的state该为open
                    parentNode.setState("open");
                    //更新数据库
                    repository.updateById(parentNode);
                }
            }

        }
    }

    /**
     * 批量删除权限的操作
     */
    public void deleteBatch(Integer id){
        //查询该节点是否有子节点
        List<Operation> chilrens = repository.findByParentId(id);
        if (chilrens != null){
            for (Operation chilren:chilrens) {
                //递归删除该节点下的所有子节点
                deleteBatch(chilren.getId());
            }
        }
        //删除当前节点
        repository.deleteById(id);
    }
    /**
     * 根据 code查询权限
     */
    public Operation findOperationByCode(String code) {
        EntityWrapper<Operation> wrapper = new EntityWrapper<>();
        if (code != null){
            wrapper.eq("code",code.trim());
        }
        List<Operation> list = repository.selectList(wrapper);
        if (list != null&&list.size()!=0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询所有的权限信息
     */
    public List<OperationDTO> findOperationByZtree() {
        List<OperationDTO> list = new ArrayList<>();

        List<Operation> parents = repository.findByRoot();
        //查询根节点下的所有 子节点
        if (parents !=null&&parents.size()!=0){
            for (Operation parent:parents
                 ) {
                OperationDTO parentDTO = toOperation(parent);
                findChildrens(parentDTO);
                list.add(parentDTO);
            }
        }
        System.out.println("list----------------》"+list);
        return list;
    }
    /**
     * 查询该节点下所有子节点
     */
    public void findChildrens(OperationDTO parentDTO){
        //查询父节点下的所有子节点
        List<Operation> chlidrens = repository.findByParentId(parentDTO.getId());
        if (chlidrens !=null&&chlidrens.size() != 0){
        //遍历查询子节点
            List<OperationDTO> dto = new ArrayList<>();
            for (Operation chlidren:chlidrens
                 ) {
                OperationDTO result = toOperation(chlidren);
                dto.add(result);
                findChildrens(result);
            }
            parentDTO.setChildren(dto);
        }
    }
    /**
     * 转换为DTO对象
     */
    public OperationDTO toOperation(Operation operation){
        OperationDTO operationDTO = new OperationDTO();
        operationDTO.setId(operation.getId());
        operationDTO.setName(operation.getName());

        return operationDTO;
    }

}

