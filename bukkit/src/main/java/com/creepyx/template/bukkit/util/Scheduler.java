package com.creepyx.template.bukkit.util;

import com.creepyx.template.bukkit.TemplatePlugin;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Scheduler {

	private static final boolean isFolia = Bukkit.getVersion().contains("Folia");

	public static void run(Runnable runnable) {
		if (isFolia) {
			Bukkit.getGlobalRegionScheduler().execute(TemplatePlugin.getPlugin(), runnable);
			return;
		}
		Bukkit.getScheduler().runTask(TemplatePlugin.getPlugin(), runnable);
	}

	public static Task runLater(Runnable runnable, long delayTicks) {
		if (isFolia) {
			return new Task(Bukkit.getGlobalRegionScheduler().runDelayed(TemplatePlugin.getPlugin(), t -> runnable.run(), delayTicks));
		}
		return new Task(Bukkit.getScheduler().runTaskLater(TemplatePlugin.getPlugin(), runnable, delayTicks));
	}

	public static Task runLater(Runnable runnable) {
		if (isFolia) {
			return new Task(Bukkit.getGlobalRegionScheduler().runDelayed(TemplatePlugin.getPlugin(), t -> runnable.run(), 1));
		}
		return new Task(Bukkit.getScheduler().runTaskLater(TemplatePlugin.getPlugin(), runnable, 1));
	}

	public static Task runTimer(Runnable runnable, long delay, long period) {
		if (isFolia) {
			return new Task(Bukkit.getGlobalRegionScheduler().runAtFixedRate(TemplatePlugin.getPlugin(), t -> runnable.run(), delay < 1 ? 1 : delay, period));
		}
		return new Task(Bukkit.getScheduler().runTaskTimer(TemplatePlugin.getPlugin(), runnable, delay, period));
	}

	public static Task runAsync(Runnable runnable) {
		if (isFolia) {
			return new Task(Bukkit.getAsyncScheduler().runNow(TemplatePlugin.getPlugin(), new Consumer<ScheduledTask>() {
				@Override
				public void accept(ScheduledTask scheduledTask) {
					runnable.run();
				}
			}));
		}
		return new Task(Bukkit.getScheduler().runTaskAsynchronously(TemplatePlugin.getPlugin(), runnable));
	}

	public static Task runDelayedAsync(Runnable runnable) {
		if (isFolia) {
			return new Task(Bukkit.getAsyncScheduler().runDelayed(TemplatePlugin.getPlugin(), new Consumer<ScheduledTask>() {
				@Override
				public void accept(ScheduledTask scheduledTask) {
					runnable.run();
				}
			}, 1, TimeUnit.MILLISECONDS));
		}
		return new Task(Bukkit.getScheduler().runTaskLaterAsynchronously(TemplatePlugin.getPlugin(), runnable, 1));
	}
	public static Task runDelayedAsync(Runnable runnable, long delay) {
		if (isFolia) {
			return new Task(Bukkit.getAsyncScheduler().runDelayed(TemplatePlugin.getPlugin(), new Consumer<ScheduledTask>() {
				@Override
				public void accept(ScheduledTask scheduledTask) {
					runnable.run();
				}
			}, delay, TimeUnit.MILLISECONDS));
		}
		return new Task(Bukkit.getScheduler().runTaskLaterAsynchronously(TemplatePlugin.getPlugin(), runnable, delay));
	}

	public static Task runTimerAsync(Runnable runnable, long delay, long period) {
		if (isFolia) {
			return new Task(Bukkit.getAsyncScheduler().runAtFixedRate(TemplatePlugin.getPlugin(), new Consumer<ScheduledTask>() {
				@Override
				public void accept(ScheduledTask scheduledTask) {
					runnable.run();
				}
			}, delay, period, TimeUnit.MILLISECONDS));
		}
		return new Task(Bukkit.getScheduler().runTaskTimerAsynchronously(TemplatePlugin.getPlugin(), runnable, delay, period));
	}

	public static Task runTimerAsync(Runnable runnable, long period) {
		if (isFolia) {
			return new Task(Bukkit.getAsyncScheduler().runAtFixedRate(TemplatePlugin.getPlugin(), new Consumer<ScheduledTask>() {
				@Override
				public void accept(ScheduledTask scheduledTask) {
					runnable.run();
				}
			}, 1, period, TimeUnit.MILLISECONDS));
		}
		return new Task(Bukkit.getScheduler().runTaskTimerAsynchronously(TemplatePlugin.getPlugin(), runnable, 1, period));
	}

	public static boolean isFolia() {
		return isFolia;
	}

	public static class Task {
		private Object foliaTask;
		private BukkitTask bukkitTask;

		Task(Object foliaTask) {
			this.foliaTask = foliaTask;
		}

		Task(BukkitTask bukkitTask) {
			this.bukkitTask = bukkitTask;
		}

		public void cancel() {
			if (foliaTask != null) ((ScheduledTask) foliaTask).cancel();
			bukkitTask.cancel();
		}
	}
}
