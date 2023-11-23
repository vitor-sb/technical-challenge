package vitorsb.project.logidataprocess.service

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import vitorsb.project.logidataprocess.dto.user.UserDTO
import vitorsb.project.logidataprocess.dto.user.UserTxtFileResponseDTO
import vitorsb.project.logidataprocess.entity.Account
import vitorsb.project.logidataprocess.exception.NotFoundException
import vitorsb.project.logidataprocess.mapper.UserMapper
import vitorsb.project.logidataprocess.repository.AccountRepository

@Service
class UserService {

    @Autowired
    private lateinit var repository: AccountRepository

    private val mapper: UserMapper = UserMapper()

    private val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun createUser(user: UserTxtFileResponseDTO): Account {
        logger.debug("M=saveUser - Saving user with id:${user.user_id}")

        val userDTO: UserDTO = mapper.toDto(user)

        return repository.save(mapper.toEntity(userDTO))
    }

    fun findById(userId: Long): Account {
        logger.debug("M=findById - Finding user by id: $userId")

        return repository.findById(userId).orElseThrow {
            throw NotFoundException("M=findById - User not found!")
        }
    }
}