package studentmanagement.DAO;

import studentmanagement.Dto.StudentDto;

import java.sql.Connection;

public interface StudentData {
    String getStudent(Connection connection);
    String saveStudent(StudentDto studentDto, Connection connection);
    String deleteStudent(String id,Connection connection);
    String updateStudent(StudentDto studentDto,Connection connection);
}
