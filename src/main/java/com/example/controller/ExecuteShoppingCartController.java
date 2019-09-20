package com.example.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.form.InsertItemForm;
import com.example.form.OrderShoppingCartForm;
import com.example.repository.OrderRepository;
import com.example.service.ExecuteShoppingCartService;

/**
 * ショッピングカートを操作するコントローラー.
 * 
 * @author takahiro.araki
 *
 */
@Controller
@RequestMapping("/Execute")
public class ExecuteShoppingCartController {
	
	@Autowired
	private ExecuteShoppingCartService executeShoppingCartService;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private HttpSession session;
	
	@ModelAttribute
	public InsertItemForm setInsertItemForm() {
		return new InsertItemForm();	
	}
	
	/**
	 * 商品をショッピングカートに入れる.
	 * @param form インプット情報
	 * @return　ショッピングカート画面
	 */
	@RequestMapping("")
	public String insertShoppingCart(InsertItemForm form ,Model model,@AuthenticationPrincipal LoginUser loginUser) {
		
		//初めてショッピングカートに商品を入れるときに、オーダーテーブルに、ユーザーIDを入れる
		//未ログイン状態の時には、仮のユーザーIDを入れる（なぜなら、ユーザーテーブルに対応した値がない）
		//未ログイン時に入れた商品を、ログイン時にも表示させたいので、未ログイン時に作成した仮のIDをログイン中のユーザーIDで書き換える。
		Integer preUserId=(Integer)session.getAttribute("preUserId");
		Integer preOrderId=(Integer)session.getAttribute("preOrderId");
		Order order;
		//未ログイン時
		if(loginUser==null) {
			if(preUserId==null) {
				//一回目のショッピングカート追加
				Random random=new Random();
				preUserId=random.nextInt(10000000);
				order=executeShoppingCartService.insertShoppingCart(form,preUserId);
				System.out.println("コントローラー検証中　"+order+"　終わり");
				model.addAttribute("order",order);
				session.setAttribute("preUserId",order.getUserId());
				session.setAttribute("preOrderId", order.getId());
			}else {
				//二回目以降のショッピングカート追加
				order=executeShoppingCartService.insertShoppingCart(form,preUserId);
				model.addAttribute("order",order);
				System.out.println("コントローラー検証中　"+order+"　終わり");
			}
		}else {
			//ログイン時
			//仮IDでログインショッピングカートに追加しておいたものを更新
			orderRepository.uploadUserId(loginUser.getUserDomain().getId(),preOrderId);
			//ログイン時に商品を追加
			order=executeShoppingCartService.insertShoppingCart(form,loginUser.getUserDomain().getId());
			model.addAttribute("order",order);
		}
		return "cart_list";
	}
	
	
	
	
	/**
	 * オーダー商品の主キーでオーダー商品を削除する.
	 * @param id オーダー商品ID
	 * @return　ショッピングカート画面
	 */
	@RequestMapping("/delete")
	public String delete(String id,Model model) {
		executeShoppingCartService.delete(id);
		return findAllById("18",model);
	}
	
	/**
	 * オーダー情報を表示する.
	 * @param id オーダーID
	 * @param model リクエストパラメーター
	 * @return ショッピングカート画面
	 */
	@RequestMapping("/findOrder")
	public String findAllById(String id,Model model) {
		Order order=executeShoppingCartService.findAllById(id);
		model.addAttribute("order",order);
		return "cart_list";
	}
	
	
	/**
	 * 注文確認画面を表示する.
	 * @param id オーダーID
	 * @param model リクエストスコープ 
	 * @return　注文確認画面 
	 */
	@RequestMapping("/showOrderConfirm")
	public String showOrderConfirm(String id,Model model) {
		Order order=executeShoppingCartService.findAllById(id);
		model.addAttribute("order",order);
		return "order_confirm";
	}
	
	
	
	/**
	 * オーダー情報を更新する.
	 * @param form 更新情報フォーム
	 * @return 注文完了画面　
	 */
	@RequestMapping("/update")
	public String updateOrder(OrderShoppingCartForm form,@AuthenticationPrincipal LoginUser loginUser) {
		executeShoppingCartService.updateOrder(form,loginUser);
		return"order_finished";
	}
}
