package ru.otus.main;

import ru.otus.common.services.db.DbService;
import ru.otus.common.services.db.DbServiceException;
import ru.otus.common.services.db.datasets.UserDataSet;
import ru.otus.backend.utils.RandomUtils;
import ru.otus.backend.hibernate.datasets.UserDataSetImpl;

import java.util.*;

/**
 * Класс имитации бурной деятельности над DbService'ом
 */
public class HardWorker implements Runnable{
    private final static long DELAY_BETWEEN_OPERATIONS = 100L;

    private DbService dbService;
    private volatile boolean isStopped;
    private Random random;

    private long maxExistingId = 0;

    enum OperationType{
        CREATE,
        READ,
        UPDATE
    }

    private SortedMap<Double,OperationType> operationProbability;

    public HardWorker(DbService dbService) {
        this.dbService = dbService;
        this.isStopped = false;

        this.random = new Random();

        operationProbability = new TreeMap<>();

        operationProbability.put(0.05,OperationType.CREATE);
        operationProbability.put(0.85,OperationType.READ);
        operationProbability.put(1.0 ,OperationType.UPDATE);

        try {
            this.doCreate();
        } catch (DbServiceException e) {
            e.printStackTrace();
        }
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    @Override
    public void run() {
        while(!this.isStopped){
            doNextOperation();

            try {
                Thread.sleep(DELAY_BETWEEN_OPERATIONS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void doNextOperation(){
        try {
            switch(this.nextOperation()){
                case READ:
                    this.doRead();
                    break;
                case CREATE:
                    this.doCreate();
                    break;
                case UPDATE:
                    this.doUpdate();
                    break;
            }
        } catch (DbServiceException e) {
            e.printStackTrace();
        }
    }

    private OperationType nextOperation(){
        double probability = this.random.nextDouble();

        for(Map.Entry<Double,OperationType> entry: this.operationProbability.entrySet()){
            if(probability<entry.getKey()){
                return entry.getValue();
            }
        }
        //в случае если у нас некорректное распределение вероятностей в карте
        return OperationType.READ;
    }

    private void doCreate() throws DbServiceException {
        UserDataSetImpl userDataSet = new UserDataSetImpl();

        setRandomValues(userDataSet);

        this.dbService.saveUser(userDataSet);

        this.maxExistingId = userDataSet.getId();
    }

    private void doUpdate() throws DbServiceException {
        UserDataSet userDataSet = this.dbService.loadUser(getExistingId());

        if(userDataSet != null) {
            setRandomValues(userDataSet);
            this.dbService.saveUser(userDataSet);
        }
    }

    private void doRead() throws DbServiceException {
        UserDataSet userDataSet = this.dbService.loadUser(getExistingId());
    }

    private void setRandomValues(UserDataSet userDataSet) {
        userDataSet.setName(RandomUtils.generateString(this.random,20));
        userDataSet.setAge(this.random.nextInt(99));
    }

    private int getExistingId() {
        return this.random.nextInt((int)maxExistingId + 1);
    }

}
