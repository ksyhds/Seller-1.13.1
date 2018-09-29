package com.Moon_Eclipse.Seller;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.Moon_eclipse.EclipseLib.LibMain;
import com.Moon_eclipse.EclipseLib.ItemCreater.ItemCreator;
import com.earth2me.essentials.api.*;
import com.gmail.Moon_Eclipse.MCgive.ItemDeliver.ItemDeliver;

public class Seller extends JavaPlugin implements Listener{

	public void onEnable(){}
	public void onDisable(){}
	
	public boolean onCommand(CommandSender sender, Command command, String Label , String[] args)
	{
		// ./팔기 교환 마테리얼이름 메타  디스플레이이름	수량	  교환할아이템
		// ./팔기 일반 마테리얼이름 메타       가격		수량
		// ./팔기 이름 마테리얼이름 메타 디스플레이이름 	가격 		수량
		// ./팔기 무시 마테리얼이름  0   디스플레이이름 	가격 		수량
		// ./ -   0  1 		  2      3   	 4 	 	 5
		
		
		/*
		 버전 업그레이드에 의해 코드를 사용하지 않게 됨
		int code = Integer.parseInt(args[1]);
		*/
		
		String Material_Name = args[1];
		byte meta = (byte) Integer.parseInt(args[2]);
		
		int before = 0;
		if(sender instanceof Player)
		{
			Player p = (Player) sender;
			Inventory inv = p.getInventory();
			
			if(sender.hasPermission("seller.sell"))
			{
				if(command.getName().equalsIgnoreCase("팔기"))
				{
					if(this.hasitem2(p, inv, Material_Name, 1))
					{
						switch(args[0])
						{
						case("일반"):
							
							double price = Double.parseDouble(args[3]);
							int tofor = Integer.parseInt(args[4]);
							//if(this.hasitem2(p, inv, code, meta, tofor))
							//{
								//Bukkit.broadcastMessage("일반을 선택하셨습니다");
								for(int i = 0 ; i<54 ; i++)
								{
									
									//Bukkit.broadcastMessage(p.getInventory().getItem(i).getAmount()+ "");
									//Bukkit.broadcastMessage(i + "");
									try
									{
										boolean b = false;
										if(
												!(inv.getItem(i).equals(null))
												&& inv.getItem(i).getType().name().equalsIgnoreCase(Material_Name)
												&& inv.getItem(i).getData().getData() == meta 
												&& inv.getItem(i).getAmount() > 0
												&& !(inv.getItem(i).getItemMeta().hasDisplayName())
											)
										{
											int amount = inv.getItem(i).getAmount();
											for(int g = 0 + before ; g < tofor; g++ )
											{
												if(amount > 0)
												{
													if(amount == 1)
													{
														inv.clear(i);
														before = g +1;
														b = true;
														this.getEconomy().add(p.getName(), price);
														break;
													}
													else
													{
														amount -= 1;
														inv.getItem(i).setAmount(amount);
														this.getEconomy().add(p.getName(), price);
														b = false;
													}
													
												}
											}
											if(b == false)
											{
												break;
											}
											if(b == true)
											{
												//Bukkit.broadcastMessage("dist < tofor");
											}
											
										
										}
									}catch(Exception e){}
									
								}
							//}
							
							break;
						case("이름"):
							// ./팔기 교환 코드 메타  디스플레이이름 교환할아이템
							// ./팔기 일반 코드 메타 	   가격	   수량
							// ./팔기 이름 코드 메타(혹은무시) 디스플레이이름 가격 수량
							// ./ -   0  1  2      3      4   5
							String Disname = args[3];
							
							String Disname2 = Disname.replace("&", "§");
							String Disname3 = Disname2.replace("_", " ");
							//Bukkit.broadcastMessage(Disname3);
							double price2 = Double.parseDouble(args[4]);
							
							int tofor2 = Integer.parseInt(args[5]);
							if(this.hasitem(p, inv, Material_Name, meta, tofor2, Disname))
							{
								//Bukkit.broadcastMessage("이름을 선택하셨습니다");
								for(int i = 0 ; i<54 ; i++)
								{
									try
									{
										boolean b = false;
										if(
												!(inv.getItem(i).equals(null))
												&& inv.getItem(i).getType().name().equalsIgnoreCase(Material_Name)
												&& inv.getItem(i).getData().getData() == meta 
												&& inv.getItem(i).getAmount() > 0
												&& inv.getItem(i).getItemMeta().hasDisplayName()
												&& inv.getItem(i).getItemMeta().getDisplayName().equals(Disname3)
										  )
											
											{
											int amount = inv.getItem(i).getAmount();
											for(int g = 0 + before ; g < tofor2; g++ )
												{
													if(amount > 0)
													{
														if(amount == 1)
														{
															inv.clear(i);
															before = g +1;
															b = true;
															this.getEconomy().add(p.getName(), price2);
															break;
														}
														else
														{
															amount -= 1;
															inv.getItem(i).setAmount(amount);
															this.getEconomy().add(p.getName(), price2);
															b = false;
														}
														
													}
												}
											if(b == false)
											{
												break;
											}
											if(b == true)
											{
												//Bukkit.broadcastMessage("dist < tofor");
											}
											

											}
									}catch(Exception e){}	
								}
							}
							
							break;
						case("교환"):
							// ./팔기 교환 코드 메타  디스플레이이름	수량	  교환할아이템
							//   -   0  1  2      3          4		5
							Disname = args[3];
							Disname2 = Disname.replace("&", "§");
							Disname3 = Disname2.replace("_", " ");
							int count = Integer.parseInt(args[4]);
							String Givename = args[5];
							if(this.hasitem(p, inv, Material_Name, meta, count, Disname3))
							{
								for(int i = 0 ; i<54 ; i++)
								{
									try
									{
										boolean b = false;
										if(
												!(inv.getItem(i).equals(null))
												&& inv.getItem(i).getType().name().equalsIgnoreCase(Material_Name)
												&& inv.getItem(i).getData().getData() == meta 
												&& inv.getItem(i).getAmount() > 0
												&& inv.getItem(i).getItemMeta().hasDisplayName()
												&& inv.getItem(i).getItemMeta().getDisplayName().equals(Disname3)
										  )
											
											{
											int amount = inv.getItem(i).getAmount();
											for(int g = 0 + before ; g < count; g++ )
												{
													if(amount > 0)
													{
														if(amount == 1)
														{
															inv.clear(i);
															before = g +1;
															b = true;
															break;
														}
														else
														{
															amount -= 1;
															inv.getItem(i).setAmount(amount);
															
															b = false;
														}
														
													}
												}
											if(b == false)
											{
												break;
											}
											if(b == true)
											{
												//Bukkit.broadcastMessage("dist < tofor");
											}
											

											}
									}catch(Exception e){}	
								}
								
								//Bukkit.dispatchCommand(sender, "주기 " + sender.getName() + " " + Givename);
								
								ItemStack give_item = ItemDeliver.ItemCreat_From_Config(Givename, 0);
								give_item =	ItemCreator.getPlaceHoldered_ItemStack(give_item, "PLAYER", sender.getName());			   					
								give_item = LibMain.hideFlags_Unbreak(give_item);
								
								inv.addItem(give_item);
								
							}
							
							break;
						case("교환2"):
							// ./팔기 교환2 마테리얼이름 메타  디스플레이이름1	수량	마테리얼이름 메타 디스플레이이름2	수량2	  교환할아이템
							//   -   0   	1  	   2      3          4	 5		  6		7		 8 		9
							Disname = args[3];
							Disname2 = Disname.replace("&", "§");
							Disname3 = Disname2.replace("_", " ");
							String Disnames = args[3];
							String Disnames2 = Disname.replace("&", "§");
							String Disnames3 = Disname2.replace("_", " ");
							String Givename1 = args[7];
							int count1 = Integer.parseInt(args[4]);
							int count2 = Integer.parseInt(args[6]);
							
							String code1 = args[1];
							String code2 = args[5];
							
							byte meta1 = (byte) Integer.parseInt(args[2]);
							byte meta2 = (byte) Integer.parseInt(args[6]);
							if(this.hasitem(p, inv, code1, meta1, count1, Disname3) && this.hasitem(p, inv, code2, meta2, count2, Disnames3))
							{
								for(int i = 0 ; i<54 ; i++)
								{
									try
									{
										boolean b = false;
										if(
												!(inv.getItem(i).equals(null))
												&& inv.getItem(i).getType().name().equalsIgnoreCase(code1) 
												&& inv.getItem(i).getData().getData() == meta1 
												&& inv.getItem(i).getAmount() > 0
												&& inv.getItem(i).getItemMeta().hasDisplayName()
												&& inv.getItem(i).getItemMeta().getDisplayName().equals(Disname3)
										  )
											
											{
											int amount = inv.getItem(i).getAmount();
											for(int g = 0 + before ; g < count1; g++ )
												{
													if(amount > 0)
													{
														if(amount == 1)
														{
															inv.clear(i);
															before = g +1;
															b = true;
															break;
														}
														else
														{
															amount -= 1;
															inv.getItem(i).setAmount(amount);
															
															b = false;
														}
														
													}
												}
											if(b == false)
											{
												break;
											}
											if(b == true)
											{
												//Bukkit.broadcastMessage("dist < tofor");
											}
											

											}
									}catch(Exception e){}	
								}
								before = 0;
								//p.sendMessage("초기화문 지나감");
								for(int i = 0 ; i<54 ; i++)
								{
									try
									{
										boolean b = false;
										if(
												!(inv.getItem(i).equals(null))
												&& inv.getItem(i).getType().name().equalsIgnoreCase(code2)
												&& inv.getItem(i).getData().getData() == meta2 
												&& inv.getItem(i).getAmount() > 0
												&& inv.getItem(i).getItemMeta().hasDisplayName()
												&& inv.getItem(i).getItemMeta().getDisplayName().equals(Disnames3)
										  )
											
											{
											int amount = inv.getItem(i).getAmount();
											for(int g = 0 + before ; g < count2; g++ )
												{
													if(amount > 0)
													{
														if(amount == 1)
														{
															inv.clear(i);
															before = g +1;
															b = true;
															break;
														}
														else
														{
															amount -= 1;
															inv.getItem(i).setAmount(amount);
															
															b = false;
														}
														
													}
												}
											if(b == false)
											{
												break;
											}
											if(b == true)
											{
												//Bukkit.broadcastMessage("dist < tofor");
											}
											

											}
									}catch(Exception e){}	
								}
								//Bukkit.dispatchCommand(sender, "주기 " + sender.getName() + " " + Givename1);
								
								ItemStack give_item = ItemDeliver.ItemCreat_From_Config(Givename1, 0);
								
								give_item =	ItemCreator.getPlaceHoldered_ItemStack(give_item, "PLAYER", sender.getName());			   					
								give_item = LibMain.hideFlags_Unbreak(give_item);
								
								inv.addItem(give_item);
							
							}
							else
							{
								p.sendMessage("§b[MCMANY]§e 아이템 §4" + code1 + "§e:§4" + meta1 + " §e혹은 §4" + code2 + "§e:§4" + meta2 + "§e 가 부족합니다.");
							}
							
							break;
						case("무시"):
							// ./팔기 교환 코드 메타  디스플레이이름 교환할아이템
							// ./팔기 일반 코드 메타 	   가격	   수량
							// ./팔기 이름 코드 메타(혹은무시) 디스플레이이름 가격 수량
							// ./ -   0  1  2      3      4   5
							String IDisname = args[3];
							
							String IDisname2 = IDisname.replace("&", "§");
							String IDisname3 = IDisname2.replace("_", " ");
							//Bukkit.broadcastMessage(Disname3);
							double Iprice2 = Double.parseDouble(args[4]);
							
							int Itofor2 = Integer.parseInt(args[5]);
							//if(this.hasitem3(p, inv, code, Itofor2, IDisname))
							//{
								//Bukkit.broadcastMessage("이름을 선택하셨습니다");
								for(int i = 0 ; i<54 ; i++)
								{
									try
									{
										boolean b = false;
										if(
												!(inv.getItem(i).equals(null))
												&& inv.getItem(i).getType().name().equalsIgnoreCase(Material_Name)
												&& inv.getItem(i).getAmount() > 0
												&& inv.getItem(i).getItemMeta().hasDisplayName()
												&& inv.getItem(i).getItemMeta().getDisplayName().equals(IDisname3)
										  )
											
											{
											int amount = inv.getItem(i).getAmount();
											for(int g = 0 + before ; g < Itofor2; g++ )
												{
													if(amount > 0)
													{
														if(amount == 1)
														{
															inv.clear(i);
															before = g +1;
															b = true;
															this.getEconomy().add(p.getName(), Iprice2);
															break;
														}
														else
														{
															amount -= 1;
															inv.getItem(i).setAmount(amount);
															this.getEconomy().add(p.getName(), Iprice2);
															b = false;
														}
														
													}
												}
											if(b == false)
											{
												break;
											}
											if(b == true)
											{
												//Bukkit.broadcastMessage("dist < tofor");
											}
											

											}
									}catch(Exception e){}	
								}
							//}
							
							break;
						}
					}
				}
				
			}
			
			// 구입 부분을 추가
			else if(sender.hasPermission("seller.buy"))
			{
				if(command.getName().equalsIgnoreCase("사기"))
				{
			    	// 사기 플레이어이름 아이템이름 가격 
			        Player target = Bukkit.getPlayer(args[0]);
			        String ItemName = args[1];
			        double price = Double.parseDouble(args[2]);

			        double balance = 0.0D;
			        try {
			          getEconomy(); balance = Economy.getMoney(target.getName()); } catch (UserDoesNotExistException e) {
			          e.printStackTrace();
			        }
			        if (balance - price < 0.0D)
			        {
			          target.sendMessage("§b[마인아레나]§e 잔액이 부족합니다.");
			        }
			        else
			        {
			          try {
			            DecreaseMoney(target, price);
			          } catch (NoLoanPermittedException e) {
			            e.printStackTrace();
			          } catch (UserDoesNotExistException e) {
			            e.printStackTrace();
			          }
			          int amount = Integer.parseInt(args[4]);
			          ItemDeliver.Send_Item(target, ItemName, amount);
			        }
			      
				}
			}
			else
			{
				sender.sendMessage("§b[MCMANY]§e 권한이 부족합니다.");
			}
		}
		else
		{
			System.out.println("[Seller] 해당 커맨드는 콘솔은 사용할 수 없습니다.");
		}
		return true;
	}
	public static final String VAULT = "Vault";
    Economy vault = null;
    boolean vaultLoaded = false;
    public Economy getEconomy(){
        if(!vaultLoaded){
            vaultLoaded = true;
            if (getServer().getPluginManager().getPlugin(VAULT) != null){
                RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
                if(rsp!=null){
                    vault = rsp.getProvider();
                }
            }
        }
        return vault;
    }
    public boolean hasitem( Player p, Inventory inv, String Material_Name, byte meta, int count, String name)
    {
    	boolean hasitem = false;
    	String Disname = name;
    	String Disname2 = Disname.replace("&", "§");
    	String Disname3 = Disname2.replace("_", " ");
    	int total = 0;
    	
    	for(int i = 0 ; i<54 ; i++)
    	{
    		try
    		{
    			if(
    					!(inv.getItem(i).equals(null))
    					&& inv.getItem(i).getType().name().equalsIgnoreCase(Material_Name)
    					&& inv.getItem(i).getData().getData() == meta 
    					&& inv.getItem(i).getAmount() > 0
    					&& inv.getItem(i).getItemMeta().hasDisplayName()
    					&& inv.getItem(i).getItemMeta().getDisplayName().equals(Disname3)
    			  )
    				{
    					total += inv.getItem(i).getAmount();
    				}
    		}catch(Exception e){}
    		
    	}
    	if(total >= count)
    	{
    		hasitem = true;
    	}
    	else
    	{
    		p.sendMessage("§b[MCMANY]§r " + Disname3 + " §e이(가) 부족합니다.");
    	}
    	return hasitem;
    }
    public boolean hasitem2( Player p, Inventory inv, String Material_Name, int count)
    {
    	boolean hasitem = false;
    	int total = 0;
    	
    	for(int i = 0 ; i<54 ; i++)
    	{
    		try
    		{
    			if(
    					!(inv.getItem(i).equals(null))
    					&& inv.getItem(i).getType().name().equalsIgnoreCase(Material_Name)
    					&& inv.getItem(i).getAmount() > 0
    			  )
    				{
    					total += inv.getItem(i).getAmount();
    				}
    		}catch(Exception e){}
    		
    	}
    	if(total >= count)
    	{
    		hasitem = true;
    	}
    	else
    	{
    		p.sendMessage("§b[MCMANY]§4 " + Material_Name + "§4" + " §e이(가) 부족합니다.");
    	}
    	return hasitem;
    }public boolean hasitem3( Player p, Inventory inv, String Material_Name, int count, String name)
    {
    	boolean hasitem = false;
    	String Disname = name;
    	String Disname2 = Disname.replace("&", "§");
    	String Disname3 = Disname2.replace("_", " ");
    	int total = 0;
    	
    	for(int i = 0 ; i<54 ; i++)
    	{
    		try
    		{
    			if(
    					!(inv.getItem(i).equals(null))
    					&& inv.getItem(i).getType().name().equalsIgnoreCase(Material_Name)
    					&& inv.getItem(i).getAmount() > 0
    					&& inv.getItem(i).getItemMeta().hasDisplayName()
    					&& inv.getItem(i).getItemMeta().getDisplayName().equals(Disname3)
    			  )
    				{
    					total += inv.getItem(i).getAmount();
    				}
    		}catch(Exception e){}
    		
    	}
    	if(total >= count)
    	{
    		hasitem = true;
    	}
    	else
    	{
    		p.sendMessage("§b[MCMANY]§4 " + Disname3 + " §e이(가) 부족합니다.");
    	}
    	return hasitem;
    }
    public void DecreaseMoney(Player p, double price) throws NoLoanPermittedException, UserDoesNotExistException {
        getEconomy(); Economy.add(p.getName(), -price);
      }
}
