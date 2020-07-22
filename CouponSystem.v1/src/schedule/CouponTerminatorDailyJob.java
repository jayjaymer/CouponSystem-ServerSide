package schedule;

import java.util.Date;
import java.util.List;

import com.jay.beans.Coupon;
import com.jay.dao.CouponsDAO;
import com.jay.dbdao.CouponsDBDAO;

public class CouponTerminatorDailyJob implements Runnable {

	/**
	 * 
	 * This class is responsible for a daily job (once in 24hours), to delete of
	 * coupons that are expired.
	 * 
	 */

	private CouponsDAO couponsDAO = new CouponsDBDAO();
	private boolean quit = false;
	private int customerID;

	public CouponTerminatorDailyJob() {
		super();
	}

	@Override
	public void run() {
		while (!quit) {
			List<Coupon> coupons = couponsDAO.getAllCoupons();
			for (Coupon coupon : coupons) {
				if (coupon.getEndDate().before(new Date(System.currentTimeMillis()))) {
					couponsDAO.deleteCouponPurchaseByCouponID(coupon.getId());
					couponsDAO.deleteCoupon(coupon.getId());
				}
			}
			try {
			//	once in 24hrs
				Thread.sleep(1000*10);
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}

	public void stopJob() {
		quit = true;
	}

}
