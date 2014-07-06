NPCFactory
==========

NPC Library for CraftBukkit mod.

Examples
=======
Here is a quick example on spawning an npc named lenis0012 without a skin:
```java
final NPCFactory factory = new NPCFactory(plugin);
final Location location = new Location(Bukkit.getWorld("world"), 0, 100, 0);
NPC npc = factory.spawnHumanNPC(location, new NPCProfile("lenis0012"));
npc.setYaw(location.getYaw());
```
But if you want the npc to have a skin, you need to construct NPCProfile in an async task.
It will grab some data from uuid.swordpvp.com.
Example to spawn lenis0012 with Notch skin:
```java
final NPCFactory factory = new NPCFactory(plugin);
final Location location = new Location(Bukkit.getWorld("world"), 0, 100, 0);
new Thread() {
	
	@Override
	public void run() {
		final NPCProfile profile = new NPCProfile("lenis0012", "Notch");
		Bukkit.getScheduler().runTask(plugin, new Runnable() {
			
			@Override
			public void run() {
				NPC npc = factory.spawnHumanNPC(location, profile);
				npc.setYaw(location.getYaw());
			}
		});
	}
}.start();
```
You can also play animations on status, they are shown in NPCAnimation.java:
```java
npc.playAnimation(NPCAnimation.SWING_ARM); //Swings npc's arm
npc.playAnimation(NPCAnimation.DAMAGE); //Displays red color on skin to mark player as hit.
```
You can also make npc's walk to certain points.
In this case, we have a player called 'player' who is 40 blocks waway from an npc.
```java
npc.pathfindTo(player.getLocation(), 0.2, 50); //Move to @player who is max 50 blocks away with a speed of 0.2 blocks per tick (4 per second)
```
And you can also make an npc look at a certain player.
I this case we have a zombie called 'zombie' and we want our npc to look at him.

Note: The npc's pitch will not change form this method!
```java
npc.lookAt(zombie); //Yup, thats all, use NULL here to stop looking at the zombie.
```
