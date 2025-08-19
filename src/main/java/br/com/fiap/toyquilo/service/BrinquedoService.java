package br.com.fiap.toyquilo.service;

import br.com.fiap.toyquilo.model.Brinquedo;
import br.com.fiap.toyquilo.repository.BrinquedoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrinquedoService {

    private final BrinquedoRepository repository;

    public List<Brinquedo> listarTodos() {
        return repository.findAll();
    }

    public Brinquedo buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brinquedo não encontrado"));
    }

    public Brinquedo salvar(Brinquedo brinquedo) {
        return repository.save(brinquedo);
    }

    public Brinquedo atualizar(Long id, Brinquedo brinquedoAtualizado) {
        Brinquedo b = buscarPorId(id);
        b.setNome(brinquedoAtualizado.getNome());
        b.setTipo(brinquedoAtualizado.getTipo());
        b.setClassificacao(brinquedoAtualizado.getClassificacao());
        b.setTamanho(brinquedoAtualizado.getTamanho());
        b.setPreco(brinquedoAtualizado.getPreco());
        return repository.save(b);
    }

    public Brinquedo atualizarParcial(Long id, Brinquedo brinquedoParcial) {
        Brinquedo b = buscarPorId(id);
        if (brinquedoParcial.getNome() != null) b.setNome(brinquedoParcial.getNome());
        if (brinquedoParcial.getTipo() != null) b.setTipo(brinquedoParcial.getTipo());
        if (brinquedoParcial.getClassificacao() != null) b.setClassificacao(brinquedoParcial.getClassificacao());
        if (brinquedoParcial.getTamanho() != null) b.setTamanho(brinquedoParcial.getTamanho());
        if (brinquedoParcial.getPreco() != null) b.setPreco(brinquedoParcial.getPreco());
        return repository.save(b);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brinquedo não encontrado");
        }
        repository.deleteById(id);
    }
}
