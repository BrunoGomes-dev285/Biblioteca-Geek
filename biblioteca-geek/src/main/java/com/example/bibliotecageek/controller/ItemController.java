package com.example.bibliotecageek.controller;

import com.example.bibliotecageek.model.Item;
import com.example.bibliotecageek.service.ItemService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/itens")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("itens", service.listarTodos());
        return "itens/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("item", new Item());
        model.addAttribute("tituloPagina", "Cadastrar item");
        return "itens/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        try {
            model.addAttribute("item", service.buscarPorId(id));
            model.addAttribute("tituloPagina", "Editar item");
            return "itens/formulario";
        } catch (IllegalArgumentException e) {
            return "redirect:/itens";
        }
    }

    @PostMapping("/salvar")
    public String salvar(
            @Valid @ModelAttribute("item") Item item,
            BindingResult resultado,
            RedirectAttributes atributos,
            Model model) {

        if (resultado.hasErrors()) {
            model.addAttribute("tituloPagina",
                    item.getId() == null ? "Cadastrar item" : "Editar item");
            return "itens/formulario";
        }

        if (item.getId() == null) {
            service.salvar(item);
            atributos.addFlashAttribute("mensagem", "Item cadastrado com sucesso!");
        } else {
            try {
                service.atualizar(item.getId(), item);
                atributos.addFlashAttribute("mensagem", "Item atualizado com sucesso!");
            } catch (IllegalArgumentException e) {
                atributos.addFlashAttribute("erro", e.getMessage());
            }
        }

        return "redirect:/itens";
    }

    @PostMapping("/excluir/{id}")
    public String excluir(
            @PathVariable Long id,
            RedirectAttributes atributos) {

        try {
            service.excluir(id);
            atributos.addFlashAttribute("mensagem", "Item excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            atributos.addFlashAttribute("erro", e.getMessage());
        }

        return "redirect:/itens";
    }
}