package com.example.bibliotecageek.service;

import com.example.bibliotecageek.model.Item;
import com.example.bibliotecageek.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> listarTodos() {
        return repository.findAll();
    }

    public Item buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Item não encontrado: " + id));
    }

    public Item salvar(Item item) {
        return repository.save(item);
    }

    public Item atualizar(Long id, Item dados) {
        Item item = buscarPorId(id);

        item.setTitulo(dados.getTitulo());
        item.setCategoria(dados.getCategoria());
        item.setAno(dados.getAno());
        item.setQuantidade(dados.getQuantidade());

        return repository.save(item);
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Item não encontrado: " + id);
        }

        repository.deleteById(id);
    }
}