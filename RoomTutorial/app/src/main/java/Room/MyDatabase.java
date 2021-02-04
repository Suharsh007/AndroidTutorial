package Room;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = Student.class , version = 1 , exportSchema =  false)
public abstract class MyDatabase extends RoomDatabase {

    public  abstract DAO dao();
}
