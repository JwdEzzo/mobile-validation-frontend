package com.example.demo.model;

import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@RequestMapping
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Item {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(nullable = false)
   private String name;

   private String description;

   @Column(nullable = false)
   private String mobileNumber;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "category_id", nullable = false)
   @JsonBackReference // Back reference - won't be serialized
   private Category category;

   public Item(String name, String description, String mobileNumber, Category category) {
      this.name = name;
      this.description = description;
      this.mobileNumber = mobileNumber;
      this.category = category;
   }

}
