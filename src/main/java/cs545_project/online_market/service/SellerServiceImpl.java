package cs545_project.online_market.service;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author knguyen93
 */
@Service
@Transactional
public class SellerServiceImpl implements SellerService {
    @Override
    public List getPendingSellers() {
        return null;
    }

    @Override
    public void approveSeller(long sellerId) throws NotFoundException {

    }

    @Override
    public void rejectSeller(long sellerId) throws NotFoundException {

    }

    @Override
    public Object getSellerById(long sellerId) throws NotFoundException {
        return null;
    }
}
