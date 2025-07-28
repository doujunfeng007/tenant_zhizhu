package com.minigod.zero.flow.workflow.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.launch.constant.AppConstant;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.flow.workflow.domain.bo.WfDesignerBo;
import com.minigod.zero.flow.workflow.domain.vo.WfDefinitionVo;
import com.minigod.zero.flow.workflow.service.IWfDefinitionService;
import com.minigod.zero.flow.workflow.utils.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 工作流程定义
 *
 * @author zsdp
 * @date 2022-01-17
 */
@Slf4j
@Api(tags = "流程定义")
@RequiredArgsConstructor
@RestController
@RequestMapping(AppConstant.BACK_API_PREFIX + "/workflow/definition")
public class WfDefinitionController{

    private final IWfDefinitionService flowDefinitionService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "流程定义列表", response = WfDefinitionVo.class)
    public R<IPage<WfDefinitionVo>> list(Query pageQuery) {
        return R.data(flowDefinitionService.list(Condition.getPage(pageQuery)));
    }

    /**
     * 列出指定流程的发布版本列表
     *
     * @param processKey 流程定义Key
     * @return
     */
    @GetMapping(value = "/publishList")
    @ApiOperation(value = "指定流程的发布版本列表", response = WfDefinitionVo.class)
    public R<IPage<WfDefinitionVo>> publishList(@ApiParam(value = "流程定义Key", required = true) @RequestParam String processKey,
                                                Query pageQuery) {
        return R.data(flowDefinitionService.publishList(processKey, Condition.getPage(pageQuery)));
    }


    @ApiOperation(value = "导入流程文件", notes = "上传bpmn20的xml文件")
    @PostMapping("/import")
    public R<Void> importFile(@RequestParam(required = false) String name,
                              @RequestParam(required = false) String category,
                              MultipartFile file) {
        if(!FileUtils.checkFormats(file.getOriginalFilename())){
            return R.fail("非法文件类型");
        }
        try (InputStream in = file.getInputStream()) {
            flowDefinitionService.importFile(name, category, in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return R.fail(e.getMessage());
        }

        return R.success("导入成功");
    }


    @ApiOperation(value = "读取xml文件")
    @GetMapping("/readXml/{definitionId}")
    public R<String> readXml(@ApiParam(value = "流程定义ID") @PathVariable(value = "definitionId") String definitionId) {
        try {
            return R.data(flowDefinitionService.readXml(definitionId) );
        } catch (Exception e) {
            return R.fail("加载xml文件异常");
        }

    }

    @ApiOperation(value = "读取图片文件")
    @GetMapping("/readImage/{definitionId}")
    public void readImage(@ApiParam(value = "流程定义id") @PathVariable(value = "definitionId") String definitionId,
                          HttpServletResponse response) {
        try (OutputStream os = response.getOutputStream()) {
            BufferedImage image = ImageIO.read(flowDefinitionService.readImage(definitionId));
            response.setContentType("image/png");
            if (image != null) {
                ImageIO.write(image, "png", os);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @ApiOperation(value = "保存流程设计器内的xml文件")
    @PostMapping("/save")
    public R<Void> save(@RequestBody WfDesignerBo bo) {
        try (InputStream in = new ByteArrayInputStream(bo.getXml().getBytes(StandardCharsets.UTF_8))) {
            flowDefinitionService.importFile(bo.getName(), bo.getCategory(), in);
        } catch (Exception e) {
            log.error("导入失败:", e);
            return R.fail(e.getMessage());
        }

        return R.success("导入成功");
    }

    @ApiOperation(value = "激活或挂起流程定义")
    @PutMapping(value = "/updateState")
    public R<Void> updateState(@ApiParam(value = "ture:挂起,false:激活", required = true) @RequestParam Boolean suspended,
                               @ApiParam(value = "流程定义ID", required = true) @RequestParam String definitionId) {
        flowDefinitionService.updateState(suspended, definitionId);
        return R.success();
    }

    @ApiOperation(value = "删除流程")
    @DeleteMapping(value = "/delete")
    public R<Void> delete(@ApiParam(value = "流程部署ID", required = true) @RequestParam String deployId) {
        flowDefinitionService.delete(deployId);
        return R.success();
    }

}
