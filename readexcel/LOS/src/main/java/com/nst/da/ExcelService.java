package com.nst.da;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ExcelService {

    private final YourEntityRepository entityRepository;

    @Autowired
    public ExcelService(YourEntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public void saveBatchData(YourEntity batchData) {
        entityRepository.save(batchData);
    }
}


