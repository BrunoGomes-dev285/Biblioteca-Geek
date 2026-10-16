package com.example.bibliotecageek.repository;

import com.example.bibliotecageek.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
