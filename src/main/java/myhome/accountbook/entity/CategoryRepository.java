package myhome.accountbook.entity;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Entity;
import javax.persistence.Id;


public interface CategoryRepository extends JpaRepository<Category, CategoryKeySet> {


}
