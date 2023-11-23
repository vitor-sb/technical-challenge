package vitorsb.project.logidataprocess.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import vitorsb.project.logidataprocess.entity.User

@Repository
interface UserRepository: JpaRepository<User, Long>