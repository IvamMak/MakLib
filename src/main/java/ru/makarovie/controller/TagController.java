package ru.makarovie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.makarovie.model.Tag;
import ru.makarovie.repo.TagRepo;

@Controller
public class TagController {
    @Autowired
    private TagRepo tagRepo;

    @GetMapping("/tags")
    public String getAllTags(Model model) {
        Iterable<Tag> tags = tagRepo.findAll();
        model.addAttribute("tags", tags);
        return "tag/allTags";
    }

    @GetMapping("tags/add")
    public String addTagForm() {
        return "tag/tag-add";
    }

    @PostMapping("/tags/add")
    public String addTag(@RequestParam("tagName") String tagName) {
        if (tagRepo.findByName(tagName).isPresent()) return "redirect:/tags";

        Tag tag = new Tag(tagName);
        tagRepo.save(tag);
        return "redirect:/tags";
    }

    @GetMapping("/tags/{id}/edit")
    public String editTagForm(@PathVariable("id") Long id, Model model) {
        if (!tagRepo.findById(id).isPresent()) return "redirect:/tags";
        Tag tag = tagRepo.findById(id).get();
        model.addAttribute("tag", tag);
        return "tag/tag-edit";
    }

    @PostMapping("/tags/{id}/edit")
    public String editTag(@PathVariable("id") Long id, @RequestParam("tagName") String tagName){
        if (!tagRepo.findById(id).isPresent()) return "redirect:/tags";
        if (tagRepo.findByName(tagName).isPresent()) return "redirect:/tags";

        Tag tag = tagRepo.findById(id).get();
        if (!tag.getName().equals(tagName) && tagName != null) {
            tag.setName(tagName);
            tagRepo.save(tag);
        }

        return "redirect:/tags";
    }
}
