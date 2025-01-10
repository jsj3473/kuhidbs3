package com.example.kuhidbs.repository.company;

import com.example.kuhidbs.entity.company.Contact;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Transactional
@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    @Modifying
    @Query("DELETE FROM Contact c WHERE c.company.companyId = :companyId")
    void deleteByCompanyId(@Param("companyId") Integer companyId);

    List<Contact> findByCompany_companyId(int companyId);
}
