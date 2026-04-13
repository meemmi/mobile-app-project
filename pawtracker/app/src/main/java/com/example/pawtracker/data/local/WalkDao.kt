package com.example.pawtracker.data.local
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface WalkDao {
    @Insert
    suspend fun insertWalk(walk: WalkEntity): Long

    @Insert
    suspend fun insertPoints(points: List<GpsPointEntity>)

    @Delete
    suspend fun deleteWalk(walk: WalkEntity)

    //History
    @Query("SELECT * FROM walks ORDER BY startTime DESC")
    fun getAllWalks(): Flow<List<WalkEntity>>

    @Query("SELECT * FROM walks WHERE startTime >= :startOfDay")
    fun getWalksFromDay(startOfDay: Long): Flow<List<WalkEntity>>

    @Query("SELECT * FROM walks WHERE startTime >= :startOfWeek")
    fun getWalksFromWeek(startOfWeek: Long): Flow<List<WalkEntity>>

    @Transaction
    @Query("SELECT * FROM walks WHERE id = :walkId")
    suspend fun getWalkWithPoints(walkId: Long): WalkWithPoints

    //Statistics
    @Query("""SELECT SUM(distance) FROM walks WHERE startTime >= :startOfDay""")
    fun getTotalDistanceSince(startOfDay: Long): Flow<Float?>

    @Query("""SELECT SUM(duration) FROM walks WHERE startTime >= :startOfWeek""")
    fun getTotalDurationSince(startOfWeek: Long): Flow<Long?>
}

