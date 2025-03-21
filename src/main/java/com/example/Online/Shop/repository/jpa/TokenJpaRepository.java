package com.example.Online.Shop.repository.jpa;

import com.example.Online.Shop.repository.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenJpaRepository extends JpaRepository<Token, Long> {

	@Query("""
			    SELECT t
			    FROM Token t
			    INNER JOIN Customer c ON t.customer.id = c.id
			    WHERE t.customer.id = :customerId
			    AND t.loggedOut = false
			""")
	List<Token> findAllAccessTokenByUser(@Param("customerId") Long customerId);

	Optional<Token> findByAccessToken(String token);

	Optional<Token> findByRefreshToken(String token);

}
