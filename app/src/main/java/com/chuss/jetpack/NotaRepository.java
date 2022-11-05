package com.chuss.jetpack;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.chuss.jetpack.db.NotaRoomDatabase;
import com.chuss.jetpack.db.dao.NotaDao;
import com.chuss.jetpack.db.entity.NotaEntity;

import java.nio.channels.AsynchronousChannelGroup;
import java.util.List;

public class NotaRepository {
    private NotaDao notaDao;
    private LiveData<List<NotaEntity>> allNotas;
    private LiveData<List<NotaEntity>> allNotasFavoritas;

    public NotaRepository(Application application){
        NotaRoomDatabase db = NotaRoomDatabase.getDatabase(application);
        notaDao = db.notaDao();
        allNotas = notaDao.getAll();
        allNotasFavoritas = notaDao.getAllfavoritas();
    }
    public LiveData<List<NotaEntity>> getAll() {return allNotas; }

    public LiveData<List<NotaEntity>> getAllFavs() {return allNotasFavoritas; }

    public void insert (NotaEntity nota){
        new insertAsyncTask(notaDao).execute(nota);
    }

    private static class insertAsyncTask extends AsyncTask<NotaEntity, Void, Void> {
        private NotaDao notaDatoAsyncTask;

        insertAsyncTask(NotaDao dao){
            notaDatoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDatoAsyncTask.insert(notaEntities[0]);
            return null;
        }
    }

    public void update (NotaEntity nota) {new updateAsyncTask(notaDao).execute(nota); }

    private static class updateAsyncTask extends AsyncTask<NotaEntity, Void, Void> {
        private NotaDao notaDatoAsyncTask;

        updateAsyncTask(NotaDao dao){
            notaDatoAsyncTask = dao;
        }

        @Override
        protected Void doInBackground(NotaEntity... notaEntities) {
            notaDatoAsyncTask.update(notaEntities[0]);
            return null;
        }
    }
}
