package com.light.saber.controller

import com.light.saber.dao.KnowledgeDao
import com.light.saber.model.Knowledge
import com.light.saber.service.KnowledgeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*
import java.util.*


@Controller
class KnowledgeController {
    @Autowired lateinit var KnowledgeDao: KnowledgeDao
    @Autowired lateinit var KnowledgeService: KnowledgeService

    @GetMapping("/")
    fun root(@RequestParam(value = "title", required = false) title: String?,
             @RequestParam(value = "pageNum", defaultValue = "1", required = false) pageNum: Int,
             @RequestParam(value = "pageSize", defaultValue = "10", required = false) pageSize: Int,
             model: Model): String {
        val page = PageRequest.of(pageNum - 1, pageSize, Sort.Direction.DESC, "id");
        val result = KnowledgeService.page(title, page)

        model.addAttribute("feeds", result.content)
        model.addAttribute("total", result.totalElements)
        model.addAttribute("pageNum", pageNum)
        model.addAttribute("pageSize", pageSize)
        model.addAttribute("title", title)
        return "index"
    }

    @PostMapping("/addKnowledge")
    @ResponseBody
    fun addKnowledge(knowledge: Knowledge): Result<String> {
        val title = knowledge.title
        val answer = knowledge.answer
        if (StringUtils.isEmpty(title)) {
            return Result(title, "问题不能为空", false)
        } else if (title.length > 100) {
            return Result(title, "问题长度不能超过100", false)
        } else if (StringUtils.isEmpty(answer)) {
            return Result(title, "答案不能为空", false)
        } else if (isTitleExist(title)) {
            return Result(title, "问题已经存在，请换一个问题", false)
        } else {
            knowledge.gmtCreate = Date()
            knowledge.gmtModified = Date()
            KnowledgeDao.save(knowledge)
            return Result(title, "保存成功", true)
        }

    }

    private fun isTitleExist(title: String): Boolean {
        val know = KnowledgeDao.selectByTitle(title)
        return !StringUtils.isEmpty(know)
    }

    @GetMapping("/knowledge/{id}")
    fun detail(@PathVariable("id") id: Long, model: Model): String {
        model.addAttribute("Knowledge", KnowledgeDao.getOne(id))
        return "detail"
    }

}