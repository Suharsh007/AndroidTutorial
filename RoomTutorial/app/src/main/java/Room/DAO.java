package Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DAO {

    @Insert
    public void studentInsertion(Student student);

    @Query("Select * from Student")
    List<Student> getStudent();

    @Query("Update Student set stuFirstName = :stuName where stuId= :stuId")
    void updateStu(String stuName , int stuId);

    @Query("Delete from Student where stuId= :stuId")
    void deleteStu(int stuId);
}
