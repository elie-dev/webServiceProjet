package web.service.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import web.service.forum.entity.Topic;

/**
 * Extension du Repository CRUD pour ajouter une méthode métier.
 *
 * @author Matthieu BACHELIER
 * @since 2021
 * @version 1.0
 */
@Repository
public interface TopicRepository extends JpaRepository<Topic, Integer> {

}


