package br.com.fiap.toyquilo.controller;

import br.com.fiap.toyquilo.model.Brinquedo;
import br.com.fiap.toyquilo.service.BrinquedoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web/brinquedos")
@RequiredArgsConstructor
public class BrinquedoWebController {

    private final BrinquedoService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("brinquedos", service.listarTodos());
        return "lista"; // lista.html
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("brinquedo", new Brinquedo());
        return "form"; // form.html
    }

    @PostMapping
    public String salvar(@ModelAttribute Brinquedo brinquedo) {
        service.salvar(brinquedo);
        return "redirect:/web/brinquedos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("brinquedo", service.buscarPorId(id));
        return "form"; // mesmo form para edição
    }

    @PatchMapping("/{id}")
    public String atualizarParcial(@PathVariable Long id, @ModelAttribute Brinquedo brinquedo) {
        service.atualizarParcial(id, brinquedo);
        return "redirect:/web/brinquedos";
    }

    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/web/brinquedos";
    }

    @PutMapping("/{id}")
    public String atualizar(@PathVariable Long id, @ModelAttribute Brinquedo brinquedo) {
        service.atualizar(id, brinquedo);
        return "redirect:/web/brinquedos";
    }
}
