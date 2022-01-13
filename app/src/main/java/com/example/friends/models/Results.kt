import android.os.Parcelable
import com.example.friends.models.Login
import com.example.friends.models.Name
import com.example.friends.models.Picture
import com.example.friends.models.Registered
import kotlinx.parcelize.Parcelize

@Parcelize
data class Results (
    val gender : String,
    val name : Name,
    val location : Location,
    val email : String,
    val login : Login,
    val dob : Dob,
    val registered : Registered,
    val phone : String,
    val cell : String,
    val id : Id,
    val picture : Picture,
    val nat : String
) : Parcelable