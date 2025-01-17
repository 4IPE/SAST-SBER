'use client'

import { useRouter } from 'next/navigation'
import { FolderGit2, Users2, UserCircle, Home, LogIn, UserPlus } from 'lucide-react'
import { ThemeToggle } from './ThemeToggle'
import { useTheme } from '@/contexts/ThemeContext'
import {useEffect, useState} from "react";

export default function Sidebar() {
  const router = useRouter()
  const { theme } = useTheme()
  const [isAuthenticated, setIsAuthenticated] = useState(false)

  useEffect(() => {
    // Проверяем наличие токена
    const token = document.cookie.includes('token')
    setIsAuthenticated(true)
  }, [])

  const iconClass = `w-6 h-6 ${theme === 'dark' ? 'text-text-primary' : 'text-text-secondary'}`

  return (
      <aside className="fixed left-0 top-0 h-full w-20 bg-card flex flex-col items-center justify-between py-8 border-r border-border">
        {isAuthenticated ? (
            <>
              {/* Показываем элементы для авторизованных пользователей */}
              <nav className="flex flex-col items-center space-y-8">
                <div
                    className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                    onClick={() => router.push('/')}
                    role="button"
                    tabIndex={0}
                >
                  <Home className={iconClass} />
                </div>

                <div
                    className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                    onClick={() => router.push('/projects')}
                    role="button"
                    tabIndex={0}
                >
                  <FolderGit2 className={iconClass} />
                </div>

                <div
                    className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                    onClick={() => router.push('/teams')}
                    role="button"
                    tabIndex={0}
                >
                  <Users2 className={iconClass} />
                </div>
              </nav>

              <div className="flex flex-col items-center space-y-8">
                <div
                    className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                    onClick={() => router.push('/profile')}
                    role="button"
                    tabIndex={0}
                >
                  <UserCircle className={iconClass} />
                </div>
                <ThemeToggle />
              </div>
            </>
        ) : (
            <>
              {/* Показываем элементы для неавторизованных пользователей */}
              <div className="flex flex-col items-center space-y-8">
                <div
                    className="p-2 rounded-full hover:bg-card-hover cursor-pointer transition-colors"
                    onClick={() => router.push('/login')}
                    role="button"
                    tabIndex={0}
                >
                  <LogIn className={iconClass} />
                </div>
                <ThemeToggle />
              </div>
            </>
        )}
      </aside>
  )
}