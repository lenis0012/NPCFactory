NPCFactory
==========

NPC Library for CraftBukkit mod.

Example
=======
Here is a quick example on spawning an npc named lenis0012 without a skin:
```final NPCFactory factory = new NPCFactory(plugin);
final Location location = new Location(Bukkit.getWorld("world"), 0, 100, 0);
NPC npc = factory.spawnHumanNPC(location, new NPCProfile("lenis0012"));
npc.setYaw(location.getYaw());```
But if you want the npc to have a skin, you need to construct NPCProfile in an async task.
It will grab some data from uuid.swordpvp.com
Exampel to spawn lenis0012 with lenis0012 skin:
```final NPCFactory factory = new NPCFactory(plugin);
final Location location = new Location(Bukkit.getWorld("world"), 0, 100, 0);
new Thread() {
	
	@Override
	public void run() {
		final NPCProfile profile = new NPCProfile("lenis0012", "lenis0012");
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				NPC npc = factory.spawnHumanNPC(location, profile);
				npc.setYaw(location.getYaw());
			}
		});
	}
}.start();```