package com.example.moviesuniverse.data.remote.staff

import com.example.moviesuniverse.data.local.staff.model.MovieStaffEntity
import com.example.moviesuniverse.data.local.staff.model.StaffDetailEntity
import com.example.moviesuniverse.data.remote.ApiResult
import com.example.moviesuniverse.data.remote.MovieStaffRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

class MovieStaffRetrofitDataSourceImpl(private val retrofitService: MoviesStaffRetrofitService) :
    MovieStaffRemoteDataSource {

    override suspend fun getMovieStaff(movieId: String): Flow<ApiResult<List<MovieStaffEntity>>> {
        return flow {
            val response = retrofitService.getMovieStaff(movieId)

            if (response.isSuccessful) {
                emit(
                    ApiResult.Success(
                        response.body()!!.map { MovieStaffEntity.fromMovieStaffResponse(it, movieId) }
                    )
                )
            } else {
                emit(ApiResult.Error(HttpException(response)))
            }
        }.catch { e ->
            emit(ApiResult.Error(e))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getStaffDetail(staffId: String): Flow<ApiResult<StaffDetailEntity>> {
        return flow {
            val response = retrofitService.getStaffDetail(staffId)

            if (response.isSuccessful) {
                emit(
                    ApiResult.Success(
                        StaffDetailEntity.fromStaffDetailResponse(response.body()!!)
                    )
                )
            } else {
                emit(ApiResult.Error(HttpException(response)))
            }
        }.catch { e ->
            emit(ApiResult.Error(e))
        }.flowOn(Dispatchers.IO)
    }
}